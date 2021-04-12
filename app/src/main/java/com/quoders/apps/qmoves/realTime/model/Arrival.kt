package com.quoders.apps.qmoves.realTime.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * DTOs representing the response of the
 * realtime service.
 */
@JsonClass(generateAdapter = true)
data class Arrivals (
    @field:Json(name = "arrivals")
    val arrivals: List<Arrival>
)

@JsonClass(generateAdapter = true)
data class Arrival (
    @field:Json(name = "line")
    val line: String,
    @field:Json(name = "arrival_time")
    val time: String
)

