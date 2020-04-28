package com.quoders.apps.qmoves.data.mapper
import com.quoders.apps.qmoves.data.Line
import com.quoders.apps.qmoves.data.Location
import com.quoders.apps.qmoves.data.Schedule
import com.quoders.apps.qmoves.data.Stop
import com.quoders.apps.qmoves.data.source.remote.RemoteLine
import com.quoders.apps.qmoves.data.source.remote.RemoteLocation
import com.quoders.apps.qmoves.data.source.remote.RemoteSchedule
import com.quoders.apps.qmoves.data.source.remote.RemoteStop

/***
 *  Mapper class that transforms RemoteLine objects in domain entity Line
 */
class RemoteLineMapper: Mapper<RemoteLine,Line>{

    private val stopListMapper = ListMapperImpl(RemoteStopMapper())
    private val locationListMapper = ListMapperImpl(RemoteLocationMapper())

    override fun map(input: RemoteLine): Line {
        val line =  Line(
            code = input.code,
            agencyId = input.agencyId,
            name = input.name,
            direction = input.direction,
            isNightLine = input.isNightLine)
        line.stops = stopListMapper.map(input.stops)
        line.route = locationListMapper.map(input.route)
        return line
    }
}

/***
 *  Mapper class that transforms RemoteStop objects in domain entity Stop
 */
class RemoteStopMapper: Mapper<RemoteStop, Stop> {
    private val locationMapper = RemoteLocationMapper()
    private val scheduleMapper = RemoteScheduleMapper()

    override fun map(input: RemoteStop): Stop {
        return  Stop(
            code = input.code,
            name = input.name,
            connections = input.connections,
            schedule = scheduleMapper.map(input.schedule),
            location = locationMapper.map(input.location)
        )
    }
}

/***
 *  Mapper class that transforms RemoteLocation objects in domain entity Location
 */
class RemoteLocationMapper: Mapper<RemoteLocation, Location> {
    override fun map(input: RemoteLocation): Location {
        return  Location(
            lat = input.lat,
            long = input.long
        )
    }
}

/***
 *  Mapper class that transforms RemoteSchedule objects in domain entity Schedule
 */
class RemoteScheduleMapper: Mapper<RemoteSchedule, Schedule> {
    override fun map(input: RemoteSchedule): Schedule {
        return  Schedule(
            workingDays = input.workingDays,
            sunday = input.sunday,
            saturday = input.saturday,
            friday = null,
            monday2Tuesday = null)
    }
}