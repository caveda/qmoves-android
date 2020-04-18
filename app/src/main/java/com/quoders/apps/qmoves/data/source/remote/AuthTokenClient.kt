package com.quoders.apps.qmoves.data.source.remote

import android.content.Context
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
import retrofit2.converter.scalars.ScalarsConverterFactory



interface AuthTokenService {
    @GET(".")
    fun getToken(@Header("token") token: String):
            Deferred<String>
}

class AuthTokenApiClient(config: FirebaseClientConfig) {

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(ScalarsConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .baseUrl(config.funcUrl)
        .build()

    val retrofitService: AuthTokenService by lazy {
        retrofit.create(AuthTokenService::class.java)
    }
}