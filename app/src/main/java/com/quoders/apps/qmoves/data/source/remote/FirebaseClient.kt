package com.quoders.apps.qmoves.data.source.remote

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.storage.FirebaseStorage
import com.quoders.apps.qmoves.data.Result
import com.quoders.apps.qmoves.data.Transport
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.tasks.await
import timber.log.Timber
import java.io.File
import java.lang.reflect.Type

/**
 * Firebase client class that interacts with Firebase to authenticate and
 * download data.
 */
class FirebaseClient(private val config: FirebaseClientConfig){

    private lateinit var auth: FirebaseAuth
    private lateinit var user: FirebaseUser
    private lateinit var storage: FirebaseStorage

    /***
     *  Fetch the list of lines of transport available in the Firebase storage
     */
    suspend fun fetchLines(transport: Transport): Result<List<RemoteLine>> {
        val initialized= initializeFirebase()
        if (!initialized) {
            Timber.w("fetchLines: Firebase could not be initialized.")
            return Result.Error(Exception("Error initializing Firebase"))
        }
        return downloadTransportData(transport)
    }

    /***
     *  Checks whether there is new data available for transport
     */
    suspend fun isNewDataAvailable (transport: Transport): Result<Boolean> {
        val initialized= initializeFirebase()
        if (!initialized) {
            Timber.w("isNewDataAvailable: Firebase could not be initialized.")
            return Result.Error(Exception("Error initializing Firebase"))
        }

        return checkNewData(storage, transport)
    }

    private suspend fun initializeFirebase(): Boolean {
        if (!this::auth.isInitialized) {
            auth = FirebaseAuth.getInstance()
            auth.addAuthStateListener {
                if (it.currentUser!=null)
                    user = it.currentUser!!
                Timber.i("initializeFirebase: User is anonymous: ${it.currentUser?.isAnonymous}")
            }
        }
        if (!this::storage.isInitialized)
            storage = FirebaseStorage.getInstance()

        var authenticated: Boolean
        if (!this::user.isInitialized || user.isAnonymous) {
            Timber.i("initializeFirebase: User not authenticated.")
            authenticated = authenticateUser(auth) != null
        }
        else {
            Timber.i("initializeFirebase: User is already authenticated: ${user.displayName}")
            authenticated = true
        }
        return authenticated
    }

    private suspend fun downloadTransportData (transport: Transport): Result<List<RemoteLine>> {
        return try {
            var dataRef = storage.reference.child(transport.dataPath)
            val localTempFile = File.createTempFile( "${transport.name}_alldata", "zip")
            dataRef.getFile(localTempFile).await()
            TransitFileLoader.loadContentFile(localTempFile.absolutePath)
        }catch (e : Exception){
            Timber.e("downloadTransportData: failure ${e.message}")
            Result.Error(e)
        }
    }

    private suspend fun authenticateUser(auth: FirebaseAuth): AuthResult? {
        return try{
            Timber.i("authenticateUser: Authenticating...")
            val authToken = getAuthToken().await()
            val data = auth
                .signInWithCustomToken(authToken.token)
                .await()
            data
        }catch (e : Exception){
            Timber.e("authenticateUser: exception catched ${e.message}")
            null
        }
    }

    private suspend fun checkNewData (storage: FirebaseStorage, transport: Transport): Result<Boolean> {
        return try{
            var metadataContentResult = fetchStorageJson(storage,transport.metadataPath)
            var newDataAvailable = false
            if (metadataContentResult is Result.Success)
            {
                val metadata = parseMetadata(metadataContentResult.data)
                if (metadata != null) {
                    newDataAvailable = metadata.hash!="0"
                }
            }
            Timber.w("isNewDataAvailable: ${newDataAvailable}")
            Result.Success(newDataAvailable)
        }catch (e : Exception){
            Timber.e("isNewDataAvailable: failure ${e.message}")
            Result.Error(e)
        }
    }

    private fun getAuthToken(): Deferred<AuthCustomToken> {
        val client = AuthTokenApiClient(config)
        return client.retrofitService.getToken(config.funcHeaderValue)
    }

    private fun parseMetadata (content: String): RemoteMetadata? {
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        val adapter: JsonAdapter<RemoteMetadata> = moshi.adapter(RemoteMetadata::class.java)
        return adapter.fromJson(content)
    }

    private fun parseTransportsList (content: String): Result<List<RemoteTransport>> {
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        val listMyData: Type = Types.newParameterizedType(
            MutableList::class.java,
            RemoteTransport::class.java
        )
        val adapter: JsonAdapter<List<RemoteTransport>> = moshi.adapter(listMyData)
        try {
            val transports = adapter.fromJson(content)
            return Result.Success(transports!!)
        }
        catch (e: Exception) {
            return Result.Error(e)
        }
    }

    suspend fun fetchTransports(): Result<List<RemoteTransport>> {
        val initialized= initializeFirebase()
        if (!initialized) {
            Timber.w("fetchTransports: Firebase could not be initialized.")
            return Result.Error(Exception("Error initializing Firebase"))
        }

        return downloadTransportsList(storage)
    }

    private suspend fun downloadTransportsList(storage: FirebaseStorage): Result<List<RemoteTransport>> {
        var fetchedContentResult = fetchStorageJson(storage,config.storageTransportPath)
        return if (fetchedContentResult is Result.Success) {
            val parseTransportsList = parseTransportsList(fetchedContentResult.data)
            parseTransportsList
        } else
        {
            val error = fetchedContentResult as Result.Error
            error
        }
    }

    private suspend fun fetchStorageJson(storage: FirebaseStorage, path: String): Result<String> {
        return try{
            var metadataRef = storage.reference.child(path)
            val bufferSize: Long = 32 * 1024
            val content = metadataRef.getBytes(bufferSize).await()
            return if (content!=null)
                        Result.Success(content.toString(Charsets.UTF_8))
                   else {
                        Timber.w("fetchStorageJson: cannot download content of $path")
                        Result.Error(Exception("Cannot get content of $path"))
                    }
        }catch (e : Exception){
            Timber.e("fetchStorageJson: failure ${e.message}")
            Result.Error(e)
        }
    }
}