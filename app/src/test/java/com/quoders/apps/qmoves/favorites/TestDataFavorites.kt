package com.quoders.apps.qmoves.favorites

import com.quoders.apps.qmoves.data.*
import com.quoders.apps.qmoves.data.source.TestDataDao

/**
 * Mocked data to use in tests involving Favorites feature
 */
class TestDataFavorites {

    companion object {

        private val transportA = Transport("transport A", Transport.TransportType.BUS, "any","any","any","any")
        private val transportB = Transport("transport B", Transport.TransportType.SUBWAY, "any","any","any","any")

        private val line1 = Line(1,"F1", "01", "dest1-orig1", Line.Direction.FORWARD, false)
        private val line2 = Line(2,"B2", "02", "dest2-orig2", Line.Direction.BACKWARD, false)

        private val defaultSchedule = Schedule("1:00", "2:00", "3:00", "6", "5")
        private val defaultLocation = Location(1.00, 2.00)
        private val stop1 = Stop("101", "Stop1",  defaultSchedule, defaultLocation,"")
        private val stop2 = Stop("202", "Stop2",  defaultSchedule, defaultLocation,"")

        val validFavoriteList = listOf(
            Favorite(transportA, line1, stop1),
            Favorite(transportB, line2, stop2)
        )
    }
}