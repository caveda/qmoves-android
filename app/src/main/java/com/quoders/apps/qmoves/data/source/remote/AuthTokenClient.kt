package com.quoders.apps.qmoves.data.source.remote

import android.content.Context
import android.os.Parcelable
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.functions.FirebaseFunctions
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.quoders.apps.qmoves.R
import com.squareup.moshi.Json
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.android.parcel.Parcelize
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query
import retrofit2.converter.scalars.ScalarsConverterFactory

/**
 * Authentication custom token to be used with Firebase
 */
@Parcelize
data class AuthCustomToken(val token: String,val ttl: Double): Parcelable

/**
 * Interface with the auth token webservice
 */
interface AuthTokenService {
    @GET(".")
    fun getToken(@Header("token") token: String):
            Deferred<AuthCustomToken>
}

/**
 * Client builder for AuthToken Service
 */
class AuthTokenApiClient(config: FirebaseClientConfig) {

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .baseUrl(config.funcUrl)
        .build()

    val retrofitService: AuthTokenService by lazy {
        retrofit.create(AuthTokenService::class.java)
    }
}