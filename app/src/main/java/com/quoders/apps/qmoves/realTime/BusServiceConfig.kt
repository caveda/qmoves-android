package com.quoders.apps.qmoves.realTime

import com.squareup.moshi.Json


/***
 * Data object containing the data of the realtime Bus service
 */
data class RemoteServicesConfig(
    @Json(name = "realtime")
    val realTimeService: RealTimeServiceConfig
)

/***
 * Data object containing the data of the realtime Bus service
 */
data class RealTimeServiceConfig(
    @Json(name = "url")
    val uri: String,
    @Json(name = "qss")
    val token: String,
    @Json(name = "sqt")
    val service: String,
    @Json(name = "sty")
    val transport: String,
    @Json(name = "scit")
    val city: String,
    @Json(name = "scht")
    val type: String
)