package com.quoders.apps.qmoves.realTime.api

import com.quoders.apps.qmoves.realTime.model.Arrivals
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Url

interface RealTimeServiceApi {
    @GET
    suspend fun getRealTime(@Url url:String, @Header("Cookie") cookies: String): Arrivals
}