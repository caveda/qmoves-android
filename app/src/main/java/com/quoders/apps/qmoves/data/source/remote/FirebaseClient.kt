package com.quoders.apps.qmoves.data.source.remote

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.quoders.apps.qmoves.data.Line
import com.quoders.apps.qmoves.data.Result
import kotlinx.coroutines.Deferred
import timber.log.Timber
import java.io.File

/**
 * Firebase client class that interacts with Firebase to authenticate and
 * download data.
 */
class FirebaseClient(val config: FirebaseClientConfig) {

    private lateinit var auth: FirebaseAuth
    private lateinit var user: FirebaseUser
    private lateinit var storage: FirebaseStorage

    /**
     * Checks if remote data has changed and updates the local
     * repository if so.
     */
    suspend fun update(onCompleted: (Result<List<Line>>) -> Any) {
        initializeFirebase()
        lateinit var data: Result<List<Line>>
        val authToken = getAuthToken().await()
        authenticateWithFirebase(authToken.token, onCompleted)
    }

    private fun authenticateWithFirebase(customToken: String, onCompleted: (Result<List<Line>>) -> Any) {
        customToken?.let {
            auth.signInWithCustomToken(it)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Timber.d("signInWithCustomToken: success")
                        user = auth.currentUser!!
                        fetchTransitData(onCompleted)
                    } else {
                        // If sign in fails, display a message to the user.
                        Timber.w("signInWithCustomToken: failure ${task.exception?.message}")
                        onCompleted(Result.Error(Exception(task.exception?.message)))
                    }
                }
        }
    }

    private fun fetchTransitData (onCompleted: (Result<List<Line>>) -> Any) {
        val storageRef = storage.reference
        var metadataRef = storageRef.child(config.storageMetadataPath)
        val bufferSize: Long = 32 * 1024
        metadataRef.getBytes(bufferSize).addOnSuccessListener {content ->
            val metadata = content!!.toString(Charsets.UTF_8)
            Timber.w("fetchDataFromFirebase: metadata ${metadata}")
            downloadTransitData(storageRef, onCompleted)
        }.addOnFailureListener { exception ->
            Timber.w("fetchDataFromFirebase: failure ${exception.message}")
            onCompleted(Result.Error(exception))
        }
    }

    private fun downloadTransitData(storageRef: StorageReference, onCompleted: (Result<List<Line>>)-> Any){

        val storageRef = storage.reference
        var metadataRef = storageRef.child(config.storageDataPath)
        val localTempFile = File.createTempFile("alldata", "zip")
        metadataRef.getFile(localTempFile).addOnSuccessListener {
            Timber.w("downloadTransitData: file ${config.storageDataPath} fetched successfully")
            val result = TransitFileLoader.loadContentFile(localTempFile.absolutePath)
            onCompleted(result)
        }.addOnFailureListener { exception ->
            Timber.w("downloadTransitData: failure ${exception.message}")
            onCompleted(Result.Error(exception))
        }
    }

    private fun initializeFirebase() {
        auth = FirebaseAuth.getInstance()
        storage = FirebaseStorage.getInstance()
    }

    private fun getAuthToken(): Deferred<AuthCustomToken> {
        val client = AuthTokenApiClient(config)
        return client.retrofitService.getToken(config.funcHeaderValue)
    }
}