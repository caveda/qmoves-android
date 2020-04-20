package com.quoders.apps.qmoves.data.source.remote

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.Deferred
import timber.log.Timber

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
    suspend fun update() {
        initializeFirebase()
        val authToken = getAuthToken().await()
        authenticateWithFirebase(authToken.token)
    }

    private fun authenticateWithFirebase(customToken: String) {
        customToken?.let {
            auth.signInWithCustomToken(it)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Timber.d("signInWithCustomToken: success")
                        user = auth.currentUser!!
                        fetchDataFromFirebase()
                    } else {
                        // If sign in fails, display a message to the user.
                        Timber.w("signInWithCustomToken: failure ${task.exception?.message}")
                    }
                }
        }
    }

    private fun fetchDataFromFirebase () {
        val storageRef = storage.reference

        var metadataRef = storageRef.child(config.storageMetadataPath)

        val bufferSize: Long = 32 * 1024
        metadataRef.getBytes(bufferSize).addOnSuccessListener {content ->
            val metadata = content!!.toString(Charsets.UTF_8)
            Timber.w("fetchDataFromFirebase: metadata ${metadata}")
        }.addOnFailureListener { exception ->
            Timber.w("fetchDataFromFirebase: failure ${exception.message}")
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