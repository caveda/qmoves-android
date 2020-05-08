package com.quoders.apps.qmoves.data.source

import com.quoders.apps.qmoves.data.Line
import com.quoders.apps.qmoves.data.source.remote.RemoteLine
import com.quoders.apps.qmoves.data.source.remote.RemoteLocation
import com.quoders.apps.qmoves.data.source.remote.RemoteSchedule
import com.quoders.apps.qmoves.data.source.remote.RemoteStop

/**
 * Mocked data to use in tests involving RemoteLines
 */
class TestDataRemote{

    companion object {
        private val defaultRemoteSchedule = RemoteSchedule("1:00", "2:00", "3:00")
        private val defaultRemoteLocation = RemoteLocation(1.00, 2.00)

        val validRemoteLines = listOf(
            RemoteLine("01", "F01", "orig1 - dest1",Line.Direction.FORWARD,
                false, generateStops("F01"), generateRoute("F01")),
            RemoteLine("01", "B01", "dest1 - orig1",Line.Direction.BACKWARD,
                false, generateStops("B01"), generateRoute("B01")),
            RemoteLine("02", "F02", "orig2 - dest2",Line.Direction.FORWARD,
                false, generateStops("F02"), generateRoute("F02")),
            RemoteLine( "02", "B02", "dest2 - orig2",Line.Direction.BACKWARD,
                false,generateStops("B02"), generateRoute("B02")),
            RemoteLine("03", "F03", "orig3 - dest3",Line.Direction.FORWARD,
                true,generateStops("F03"), generateRoute("F03")),
            RemoteLine("03", "B03", "dest3 - orig3",Line.Direction.BACKWARD,
                 true,generateStops("B03"), generateRoute("B03"))
        )

        private fun generateStops(lineId: String): List<RemoteStop> {
            val stops = mutableListOf<RemoteStop>()
            (1..5).forEach{
                    val stop = RemoteStop(
                        code = it.toString(),
                        name = "Stop $it of $lineId",
                        schedule = defaultRemoteSchedule,
                        location = defaultRemoteLocation,
                        connections = "")
                    stops.add(stop)
                }
            return stops
        }

        private fun generateRoute(lineId: String): List<RemoteLocation> {
            val route = mutableListOf<RemoteLocation>()
            (1..5).forEach{ route.add(defaultRemoteLocation) }
            return route
        }
    }
}