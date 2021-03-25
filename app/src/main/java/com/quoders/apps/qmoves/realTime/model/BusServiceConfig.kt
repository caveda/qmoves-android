package com.quoders.apps.qmoves.realTime.model

import android.net.Uri
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


/***
 * Data object containing the data of the realtime Bus service
 */
@JsonClass(generateAdapter = true)
data class RemoteServicesConfig(
    @field:Json(name = "realtime")
    val realTimeService: RealTimeServiceConfig
)

/***
 * Data object containing the data of the realtime Bus service
 */
@JsonClass(generateAdapter = true)
data class RealTimeServiceConfig(
    @field:Json(name = "sch")
    val scheme: String,
    @field:Json(name = "url")
    val uri: String,
    @field:Json(name = "qss")
    val token: String,
    @field:Json(name = "sqt")
    val service: String,
    @field:Json(name = "sty")
    val transport: String,
    @field:Json(name = "scit")
    val city: String,
    @field:Json(name = "scht")
    val type: String,
    @field:Json(name = "alg")
    val algorithm: String,
    @field:Json(name = "text")
    val text: String,
    @field:Json(name = "content")
    val content: String
) {

    val serviceBaseUri : Uri
        get() =  Uri.Builder()
                    .scheme(scheme)
                    .authority(uri)
                    .build()

    fun getStopQueryPath(id :String) : Uri {
        return Uri.Builder()
            .appendPath(service)
            .appendPath(city)
            .appendPath(transport)
            .appendPath(type)
            .appendPath(id)
            .build()
    }
}