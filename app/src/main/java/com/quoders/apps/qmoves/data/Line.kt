package com.quoders.apps.qmoves.data

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

/**
 Data class representing a line of a transit
 */
@Parcelize
//@JsonClass(generateAdapter = true)
data class Line (
    @Json(name = "Id") val id: String,
    @Json(name = "AgencyId") val agencyId: String,
    @Json(name = "Name") val name: String,
    @Json(name = "Dir") val direction: Direction,
    @Json(name = "Night") val isNightLine: Boolean,
    @Json(name = "Stops") val stops: MutableList<Stop> = mutableListOf<Stop>(),
    @Json(name = "Map") val route: MutableList<Location> = mutableListOf<Location>()): Parcelable {

    val uniqueId : String
        get() = agencyId + direction.code

    val type : LineType = if (isNightLine) LineType.NIGHT else LineType.REGULAR

    enum class Direction (val direction: String, val code: String){
        FORWARD("FORWARD", "F"),
        BACKWARD("BACKWARD", "B")
    }

    enum class LineType {
        REGULAR,
        NIGHT
    }
}