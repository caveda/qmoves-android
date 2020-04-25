package com.quoders.apps.qmoves.lines

import com.quoders.apps.qmoves.data.Line

/**
 * Mocked data to use in tests involving Line lists
 */
class MoqLinesData {

    companion object {
        val validLineList = listOf(
            Line(0,0,"I1", "01", "orig1-dest1", Line.Direction.FORWARD, false),
            Line(1,0,"V1", "01", "dest1-orig1", Line.Direction.BACKWARD, false),
            Line(2,0,"I2", "02", "orig2-dest2", Line.Direction.FORWARD, false),
            Line(3,0,"V2", "02", "orig2-dest2", Line.Direction.BACKWARD, false),
            Line(4,0,"I3", "03", "orig3-dest3", Line.Direction.FORWARD, true),
            Line(5,0,"V3", "03", "orig3-dest3", Line.Direction.BACKWARD, true)
        )
    }
}