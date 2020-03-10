package com.quoders.apps.qmoves.lines

import com.quoders.apps.qmoves.data.Line

/**
 * Mocked data to use in tests involving Line lists
 */
class MoqLinesData {

    companion object {
        val validLineList = listOf(
            Line("1", "orig1-dest1", Line.Direction.FORWARD, Line.LineType.REGULAR),
            Line("1", "dest1-orig1", Line.Direction.BACKWARD, Line.LineType.REGULAR),
            Line("2", "orig2-dest2", Line.Direction.FORWARD, Line.LineType.REGULAR),
            Line("2", "orig2-dest2", Line.Direction.BACKWARD, Line.LineType.REGULAR),
            Line("3", "orig3-dest3", Line.Direction.FORWARD, Line.LineType.NIGHT),
            Line("3", "orig3-dest3", Line.Direction.BACKWARD, Line.LineType.NIGHT)
        )
    }
}