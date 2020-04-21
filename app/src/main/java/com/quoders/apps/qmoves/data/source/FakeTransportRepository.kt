package com.quoders.apps.qmoves.data.source

import com.quoders.apps.qmoves.data.*
import kotlin.random.Random
import kotlin.random.nextInt

/**
 * Fake implementation of a transport repository
 */
class FakeTransportRepository : TransportRepository{

    override suspend fun getLines(): Result<List<Line>>{
        val lines = mutableListOf<Line>()
        (1..30)
            .map { it.toString().padStart(2, '0') }
            .forEach{
                lines.add (createLine(it, Line.Direction.FORWARD))
                lines.add (createLine(it, Line.Direction.BACKWARD))}
        return Result.Success(lines)
    }

    private fun createLine(id: String, direction: Line.Direction): Line {
        val line =  Line (
            id = id,
            agencyId = id,
            name = if (direction== Line.Direction.FORWARD) "Origin $id - Destination $id"
                    else "Destination $id -Origin $id",
            direction = direction,
            isNightLine = false
            )
        line.stops.addAll(createStops(line))
        return line
    }

    private fun createStops (line: Line) : List<Stop> {
        val stops = mutableListOf<Stop>()
        (1..Random.nextInt(10..20))
            .forEach{
                val stop = Stop(
                    Random.nextInt(1000..9999).toString(),
                    "Stop $it of ${line.uniqueId}",
                    generateSchedule(),
                    generateLocation(it),
                    null
                )
                stops.add(stop)
            }
        return stops
    }

    private fun generateSchedule (): Schedule {
        return Schedule("7:10,7:30..22:20",
            "",
            "7:40..22:00",
            "9:10..23:00",
            "10:00..21:15")
    }

    private fun generateLocation (i: Int): Location {
        return Location(
            40.4378698 + (.001 * i),
            -3.8196207 +  (.001 * i)
        )
    }
}