package com.quoders.apps.qmoves.data

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize

/**
 Data class representing a line of a transit
 */
@Parcelize
data class Line (
    @Json(name = "Id") var code: String,
    @Json(name = "AgencyId") var agencyId: String,
    @Json(name = "Name") var name: String,
    @Json(name = "Dir") var direction: Direction = Direction.FORWARD,
    @Json(name = "Night") var isNightLine: Boolean,
    @Json(name = "Stops") var stops: MutableList<Stop> = mutableListOf(),
    @Json(name = "Map") var route: MutableList<Location> = mutableListOf())
    : Parcelable {

    @IgnoredOnParcel
    val uniqueId : String = agencyId + direction.code

    @IgnoredOnParcel
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