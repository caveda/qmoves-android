package com.quoders.apps.qmoves.data.source.remote

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.storage.FirebaseStorage
import com.quoders.apps.qmoves.data.Result
import com.quoders.apps.qmoves.data.Transport
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.tasks.await
import timber.log.Timber
import java.io.File

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

        return checkNewData(storage)
    }

    private suspend fun initializeFirebase(): Boolean {
        auth = FirebaseAuth.getInstance()
        storage = FirebaseStorage.getInstance()
        var authenticated = false

        if (auth.currentUser!=null)
            authenticated = authenticateUser(auth) !=null

        return authenticated
    }

    private suspend fun downloadTransportData (transport: Transport): Result<List<RemoteLine>> {
        return try {
            var metadataRef = storage.reference.child(config.storageDataPath)
            val localTempFile = File.createTempFile( "${transport.name}_alldata", "zip")
            metadataRef.getFile(localTempFile).await()
            TransitFileLoader.loadContentFile(localTempFile.absolutePath)
        }catch (e : Exception){
            Timber.w("downloadTransportData: failure ${e.message}")
            Result.Error(e)
        }
    }

    private suspend fun authenticateUser(auth: FirebaseAuth): AuthResult? {
        return try{
            val authToken = getAuthToken().await()
            val data = auth
                .signInWithCustomToken(authToken.token)
                .await()
            data
        }catch (e : Exception){
            null
        }
    }

    private suspend fun checkNewData (storage: FirebaseStorage): Result<Boolean> {
        return try{
            var metadataRef = storage.reference.child(config.storageMetadataPath)
            val bufferSize: Long = 32 * 1024
            val content = metadataRef.getBytes(bufferSize).await()
            var newDataAvailable = false
            content?.let {
                val metadata = parseMetadata(it.toString(Charsets.UTF_8))
                if (metadata != null) {
                    newDataAvailable = metadata.hash!="0"
                }
            }
            Timber.w("isNewDataAvailable: ${newDataAvailable}")
            Result.Success(newDataAvailable)
        }catch (e : Exception){
            Timber.w("isNewDataAvailable: failure ${e.message}")
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
}