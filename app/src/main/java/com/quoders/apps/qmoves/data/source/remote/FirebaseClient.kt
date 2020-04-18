package com.quoders.apps.qmoves.data.source.remote

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.functions.FirebaseFunctions
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.quoders.apps.qmoves.R
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

class FirebaseClient(val config: FirebaseClientConfig) {

    private lateinit var auth: FirebaseAuth

    /**
     * Checks if remote data has changed and updates the local
     * repository if so.
     */
    suspend fun update() {
        initializeFirebase()
        val tokenDeferred = getAuthToken()
        val token = tokenDeferred.await()
    }

    private fun initializeFirebase() {
        auth = FirebaseAuth.getInstance()
    }

    private fun getAuthToken(): Deferred<String> {
        val client = AuthTokenApiClient(config)
        return client.retrofitService.getToken(config.funcHeaderValue)
    }

}