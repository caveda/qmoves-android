package com.quoders.apps.qmoves.realTime.model

import com.squareup.moshi.Json

/**
 * DTOs representing the response of the
 * realtime service.
 */
data class Arrivals (
    @Json(name = "arrivals")
    val arrivals: List<Arrival>
)

data class Arrival (
    @Json(name = "line")
    val line: String,
    @Json(name = "arrival_time")
    val time: String
)

