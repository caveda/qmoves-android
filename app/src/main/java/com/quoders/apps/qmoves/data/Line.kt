package com.quoders.apps.qmoves.data

/**
 Data class representing a line of a transit
 */
data class Line (
    var agencyId: String,
    var name: String,
    var direction: Direction,
    var type: LineType,
    var stops: List<Stop>? = null,
    var route: List<Location>? = null
){
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