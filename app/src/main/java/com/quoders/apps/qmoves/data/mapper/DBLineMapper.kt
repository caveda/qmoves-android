package com.quoders.apps.qmoves.data.mapper
import com.quoders.apps.qmoves.data.Line
import com.quoders.apps.qmoves.data.Location
import com.quoders.apps.qmoves.data.Schedule
import com.quoders.apps.qmoves.data.Stop
import com.quoders.apps.qmoves.data.source.local.DBLine
import com.quoders.apps.qmoves.data.source.local.DBLocation
import com.quoders.apps.qmoves.data.source.local.DBSchedule
import com.quoders.apps.qmoves.data.source.local.DBStop

/***
 *  Mapper class that transforms DBLine objects in domain entity Line
 */
class DBLineMapper: Mapper<DBLine,Line>{

    private val stopListMapper = ListMapperImpl(DBStopMapper())
    private val locationListMapper = ListMapperImpl(DBLocationMapper())

    override fun map(input: DBLine): Line {
        return  Line(
            uuid = input.lineId,
            code = input.code,
            agencyId = input.agencyId,
            name = input.name,
            direction = input.direction,
            isNightLine = input.isNightLine)
    }
}

/***
 *  Mapper class that transforms DBStop objects in domain entity Stop
 */
class DBStopMapper: Mapper<DBStop, Stop> {
    private val locationMapper = DBLocationMapper()
    private val scheduleMapper = DBScheduleMapper()

    override fun map(input: DBStop): Stop {
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
 *  Mapper class that transforms DBLocation objects in domain entity Location
 */
class DBLocationMapper: Mapper<DBLocation, Location> {
    override fun map(input: DBLocation): Location {
        return  Location(
            lat = input.lat,
            long = input.long
        )
    }
}

/***
 *  Mapper class that transforms DBSchedule objects in domain entity Schedule
 */
class DBScheduleMapper: Mapper<DBSchedule, Schedule> {
    override fun map(input: DBSchedule): Schedule {
        return  Schedule(
            workingDays = input.workingDays,
            sunday = input.sunday,
            saturday = input.saturday,
            friday = null,
            monday2Tuesday = null)
    }
}