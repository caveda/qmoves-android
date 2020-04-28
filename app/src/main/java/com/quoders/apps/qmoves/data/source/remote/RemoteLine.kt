package com.quoders.apps.qmoves.data.source.remote

import com.quoders.apps.qmoves.data.Line
import com.squareup.moshi.Json

/***
 * DTO class representing a Line object returned from remote source.
 */
data class RemoteLine(
    @Json(name = "Id")
    val code: String,
    @Json(name = "AgencyId")
    val agencyId: String,
    @Json(name = "Name")
    val name: String,
    @Json(name = "Dir")
    val direction: Line.Direction,
    @Json(name = "Night")
    val isNightLine: Boolean,
    @Json(name = "Stops")
    val stops: List<RemoteStop>,
    @Json(name = "Map")
    val route: List<RemoteLocation>)
