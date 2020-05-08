package com.quoders.apps.qmoves.data.source.remote

import com.squareup.moshi.Json

/***
 * DTO class representing a Stop object returned from remote source.
 */
data class RemoteStop (
    @Json(name = "Id")
    val code: String,
    @Json(name = "Na")
    val name: String,
    @Json(name = "Sc")
    val schedule: RemoteSchedule,
    @Json(name = "Lc")
    val location: RemoteLocation,
    @Json(name = "Co")
    val connections: String?)


data class RemoteSchedule (
    @Json(name = "Wor")
    val workingDays: String?,
    @Json(name = "Sat")
    val saturday: String?,
    @Json(name = "Sun")
    val sunday: String?
)