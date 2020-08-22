package com.quoders.apps.qmoves.data.source

import com.quoders.apps.qmoves.data.*
import kotlin.random.Random
import kotlin.random.nextInt

/**
 * Fake implementation of a transport repository
 */
class FakeTransportRepository : TransportRepository{

    private var data : List<Line> = listOf()
    override suspend fun getTransports(): Result<List<Transport>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun getLines(agency: Transport): Result<List<Line>>{
        if (data.isNotEmpty())
            return Result.Success(data)
        data=generateFakeLines()
        return Result.Success(data)
    }

    override suspend fun getLineStops(line: Line): Result<List<Stop>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun getRoute(line: Line): Result<List<Location>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun addFavorite(favorite: Favorite) {
        TODO("Not yet implemented")
    }

    override suspend fun getAllFavorites(): Result<List<Favorite>> {
        TODO("Not yet implemented")
    }

    private fun generateFakeLines(): List<Line> {
        val lines = mutableListOf<Line>()
        (1..30)
            .map { it.toString().padStart(2, '0') }
            .forEach {
                lines.add(createLine(it, Line.Direction.FORWARD))
                lines.add(createLine(it, Line.Direction.BACKWARD))
            }
        return lines
    }

    fun setLines (lines: List<Line>) {
        data = lines
    }

    private fun createLine(id: String, direction: Line.Direction): Line {
        val line =  Line (
            code = id,
            agencyId = id,
            name = if (direction== Line.Direction.FORWARD) "Origin $id - Destination $id"
                    else "Destination $id -Origin $id",
            direction = direction,
            isNightLine = false)
        line.stops = createStops(line.uniqueId)
        return line
    }

    private fun createStops (lineId: String) : List<Stop> {
        val stops = mutableListOf<Stop>()
        (1..Random.nextInt(10..20))
            .forEach{
                val stop = Stop(
                    code = Random.nextInt(1000..9999).toString(),
                    name = "Stop $it of ${lineId}",
                    schedule = generateSchedule(),
                    location = generateLocation(it))
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
            lat = 40.4378698 + (.001 * i),
            long = -3.8196207 +  (.001 * i)
        )
    }
}