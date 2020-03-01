package com.quoders.apps.qmoves.data

/*
 Data class representing a line of a transit
 */
data class Line (
    var id : String,
    var agencyId: String,
    var number: Long,
    var name: String,
    var direction: Direction,
    var type: LineType,
    var stops: List<Stop>,
    var route: List<Location>
){

    enum class Direction (val direction: String){
        FORWARD("FORWARD"),
        BACKWARD("BACKWARD")
    }

    enum class LineType {
        REGULAR,
        NIGHT
    }
}