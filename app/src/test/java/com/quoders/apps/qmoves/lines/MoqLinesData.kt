package com.quoders.apps.qmoves.lines

import com.quoders.apps.qmoves.data.Line

/**
 * Mocked data to use in tests involving Line lists
 */
class MoqLinesData {

    companion object {
        val validLineList = listOf(
            Line("I1", "01", "orig1-dest1", Line.Direction.FORWARD, false),
            Line("V1", "01", "dest1-orig1", Line.Direction.BACKWARD, false),
            Line("I2", "02", "orig2-dest2", Line.Direction.FORWARD, false),
            Line("V2", "02", "orig2-dest2", Line.Direction.BACKWARD, false),
            Line("I3", "03", "orig3-dest3", Line.Direction.FORWARD, true),
            Line("V3", "03", "orig3-dest3", Line.Direction.BACKWARD, true)
        )
    }
}