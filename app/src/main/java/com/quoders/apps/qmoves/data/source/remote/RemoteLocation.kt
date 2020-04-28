package com.quoders.apps.qmoves.data.source.remote

import com.squareup.moshi.Json

/**
 * DTO object representing a location returned from remote source.
 */
data class RemoteLocation (
    @Json(name = "La")
    val lat: Double,
    @Json(name = "Lo")
    val long: Double
)