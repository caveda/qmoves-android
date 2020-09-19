package com.quoders.apps.qmoves.data.source

import com.quoders.apps.qmoves.data.*
import com.quoders.apps.qmoves.data.source.local.*

/**
 * Mocked data to use in tests involving DBLines
 */
class TestDataDao{

    companion object {

        const val transportName = "Bus"
        const val transportColor = "0xFF0000"

        val validTransport = Transport(transportName, Transport.TransportType.BUS,transportColor,"any","any","any")

        val validDBTransport = DBTransport(1, transportName, transportColor, Transport.TransportType.BUS, "any", "any", "any")

        val validSingleDBLine = DBLine(1,transportName, "01", "F01", "orig1 - dest1",Line.Direction.FORWARD, false)
        val validDBLineList = listOf(
            DBLine(1,transportName, "01", "F01", "orig1 - dest1",Line.Direction.FORWARD, false),
            DBLine(2,transportName, "01", "B01", "dest1 - orig1",Line.Direction.BACKWARD, false),
            DBLine(3,transportName, "02", "I02", "orig2 - dest2",Line.Direction.FORWARD, false),
            DBLine(4,transportName, "02", "B02", "dest2 - orig2",Line.Direction.BACKWARD, false),
            DBLine(5,transportName, "03", "I03", "orig3 - dest3",Line.Direction.FORWARD, true),
            DBLine(6,transportName, "03", "B03", "dest3 - orig3",Line.Direction.BACKWARD, true)
        )

        val validSingleLine = Line(1, "01", "F01", "orig1 - dest1",Line.Direction.FORWARD, false)
        val validLineList = listOf(
            Line(1, "01", "F01", "orig1 - dest1",Line.Direction.FORWARD, false),
            Line(2, "01", "B01", "dest1 - orig1",Line.Direction.BACKWARD, false),
            Line(3, "02", "I02", "orig2 - dest2",Line.Direction.FORWARD, false),
            Line(4, "02", "B02", "dest2 - orig2",Line.Direction.BACKWARD, false),
            Line(5, "03", "I03", "orig3 - dest3",Line.Direction.FORWARD, true),
            Line(6, "03", "B03", "dest3 - orig3",Line.Direction.BACKWARD, true)
        )

        private val defaultDBSchedule = DBSchedule("1:00", "2:00", "3:00", "6", "5")
        private val defaultDBLocation = DBLocation(1.00, 2.00)
        val validListDBStops = listOf(
            DBStop(1, 1, "101", "Stop1",  defaultDBSchedule, defaultDBLocation,""),
            DBStop(2, 1, "102", "Stop2",  defaultDBSchedule, defaultDBLocation,""),
            DBStop(3, 1, "103", "Stop3",  defaultDBSchedule, defaultDBLocation,""),
            DBStop(4, 4, "201", "Stop201",  defaultDBSchedule, defaultDBLocation,""),
            DBStop(5, 4, "202", "Stop202",  defaultDBSchedule, defaultDBLocation,""),
            DBStop(6, 4, "203", "Stop203",  defaultDBSchedule, defaultDBLocation,"")
        )

        private val defaultSchedule = Schedule("1:00", "2:00", "3:00", "6", "5")
        private val defaultLocation = Location(1.00, 2.00)
        val validListStops = listOf(
            Stop("101", "Stop1",  defaultSchedule, defaultLocation,""),
            Stop( "102", "Stop2",  defaultSchedule, defaultLocation,""),
            Stop( "103", "Stop3",  defaultSchedule, defaultLocation,""),
            Stop("201", "Stop201",  defaultSchedule, defaultLocation,""),
            Stop( "202", "Stop202",  defaultSchedule, defaultLocation,""),
            Stop( "203", "Stop203",  defaultSchedule, defaultLocation,"")
        )

        val validDBLineWithStops= LineWithStops (
                line = validSingleDBLine,
                stops = validListDBStops)

        private val validDBRoute = MutableList(4) { i ->
            DBRouteLocation((i+1).toLong(),1,defaultDBLocation)}

        val validRoute = MutableList(4) {_ -> defaultLocation}

        val validDBLineWithRoute = LineWithRoute (
            line = validSingleDBLine,
            route = validDBRoute)

        val validDBLineWithFavoriteStops = LineWithStops (
            line = DBLine(1,transportName, "01", "F01", "orig1 - dest1",Line.Direction.FORWARD, false),
            stops = listOf(
                DBStop(1, 1, "101", "Stop1",  defaultDBSchedule, defaultDBLocation,""),
                DBStop(2, 1, "102", "Stop2",  defaultDBSchedule, defaultDBLocation,"")
            )
        )

        val validFavoriteLineCode = "F01"
        val validDBFavoriteList = listOf(
            DBFavorite(stopCode = "101", transportName = transportName, lineCode = validFavoriteLineCode),
            DBFavorite(stopCode = "102", transportName = transportName, lineCode = validFavoriteLineCode)
        )
    }
}