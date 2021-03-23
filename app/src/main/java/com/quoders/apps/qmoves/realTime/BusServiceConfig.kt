package com.quoders.apps.qmoves.realTime

import android.net.Uri
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


/***
 * Data object containing the data of the realtime Bus service
 */
@JsonClass(generateAdapter = true)
data class RemoteServicesConfig(
    @Json(name = "realtime")
    val realTimeService: RealTimeServiceConfig
)

/***
 * Data object containing the data of the realtime Bus service
 */
@JsonClass(generateAdapter = true)
data class RealTimeServiceConfig(
    @Json(name = "sch")
    val schema: String,
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
) {

    fun getTransportQueryUri(id :String) : Uri {
        return Uri.Builder()
            .scheme(schema)
            .authority(uri)
            .appendPath(service)
            .appendPath(city)
            .appendPath(transport)
            .appendPath(type)
            .appendPath(id)
            .build()
    }
}