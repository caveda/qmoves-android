package com.quoders.apps.qmoves.lines

import com.quoders.apps.qmoves.data.Line

/**
 * Mocked data to use in tests involving Line lists
 */
class TestDataLines {

    companion object {
        val validLineList = listOf(
            Line(1,"I1", "01", "orig1-dest1", Line.Direction.FORWARD, false),
            Line(2,"V1", "01", "dest1-orig1", Line.Direction.BACKWARD, false),
            Line(3,"I2", "02", "orig2-dest2", Line.Direction.FORWARD, false),
            Line(4,"V2", "02", "orig2-dest2", Line.Direction.BACKWARD, false),
            Line(5,"I3", "03", "orig3-dest3", Line.Direction.FORWARD, true),
            Line(6,"V3", "03", "orig3-dest3", Line.Direction.BACKWARD, true)
        )
    }
}