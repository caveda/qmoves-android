package com.quoders.apps.qmoves.data.source

import com.quoders.apps.qmoves.data.Line
import com.quoders.apps.qmoves.data.Result

/**
 * Fake implementation of a transport repository
 */
class FakeTransportRepository : TransportRepository{

    override suspend fun getLines(): Result<List<Line>> {
        val lines = mutableListOf<Line>()
        (1..30)
            .map { it.toString().padStart(1, '0') }
            .forEach{
                lines.add (createLine(it, Line.Direction.FORWARD))
                lines.add (createLine(it, Line.Direction.BACKWARD))}
        return Result.Success(lines)
    }

    private fun createLine(id: String, direction: Line.Direction): Line {
        return Line (
            agencyId = id,
            name = if (direction== Line.Direction.FORWARD) "Origin - Destination" else "Destination-Origin",
            direction = direction,
            type = Line.LineType.REGULAR)
    }
}