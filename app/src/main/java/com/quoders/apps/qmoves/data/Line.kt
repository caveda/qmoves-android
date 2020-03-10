package com.quoders.apps.qmoves.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 Data class representing a line of a transit
 */
@Parcelize
data class Line (
    val agencyId: String,
    val name: String,
    val direction: Direction,
    val type: LineType,
    val stops: MutableList<Stop> = mutableListOf<Stop>(),
    val route: MutableList<Location> = mutableListOf<Location>()): Parcelable {

    val uniqueId : String
        get() = agencyId + direction.code

    enum class Direction (val direction: String, val code: String){
        FORWARD("FORWARD", "F"),
        BACKWARD("BACKWARD", "B")
    }

    enum class LineType {
        REGULAR,
        NIGHT
    }
}