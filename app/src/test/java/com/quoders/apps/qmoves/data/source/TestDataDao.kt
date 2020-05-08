package com.quoders.apps.qmoves.data.source

import com.quoders.apps.qmoves.data.Line
import com.quoders.apps.qmoves.data.source.local.DBLine

/**
 * Mocked data to use in tests involving DBLines
 */
class TestDataDao{

    companion object {
        val transportName = "Bus"

        val validDBLineList = listOf(
            DBLine(1,transportName, "01", "F01", "orig1 - dest1",Line.Direction.FORWARD, false),
            DBLine(2,transportName, "01", "B01", "dest1 - orig1",Line.Direction.BACKWARD, false),
            DBLine(3,transportName, "02", "I02", "orig2 - dest2",Line.Direction.FORWARD, false),
            DBLine(4,transportName, "02", "B02", "dest2 - orig2",Line.Direction.BACKWARD, false),
            DBLine(5,transportName, "03", "I03", "orig3 - dest3",Line.Direction.FORWARD, true),
            DBLine(6,transportName, "03", "B03", "dest3 - orig3",Line.Direction.BACKWARD, true)
        )
        val validLineList = listOf(
            Line(1, "01", "F01", "orig1 - dest1",Line.Direction.FORWARD, false),
            Line(2, "01", "B01", "dest1 - orig1",Line.Direction.BACKWARD, false),
            Line(3, "02", "I02", "orig2 - dest2",Line.Direction.FORWARD, false),
            Line(4, "02", "B02", "dest2 - orig2",Line.Direction.BACKWARD, false),
            Line(5, "03", "I03", "orig3 - dest3",Line.Direction.FORWARD, true),
            Line(6, "03", "B03", "dest3 - orig3",Line.Direction.BACKWARD, true)
        )
    }
}