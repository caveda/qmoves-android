package com.quoders.apps.qmoves.data

import android.os.Parcelable
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize

/**
 Data class representing domain entity: line of a transport
 */
@Parcelize
data class Line (
    val uuid: Long = 0L,
    val code: String,
    val agencyId: String,
    val name: String,
    val direction: Direction = Direction.FORWARD,
    val isNightLine: Boolean)
    : Parcelable {


    var stops: List<Stop> = listOf()

    var route: List<Location> = listOf()

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