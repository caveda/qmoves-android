package com.quoders.apps.qmoves.data.mapper
import com.quoders.apps.qmoves.data.Transport
import com.quoders.apps.qmoves.data.source.local.*
import com.quoders.apps.qmoves.data.source.remote.RemoteLine
import com.quoders.apps.qmoves.data.source.remote.RemoteLocation
import com.quoders.apps.qmoves.data.source.remote.RemoteSchedule
import com.quoders.apps.qmoves.data.source.remote.RemoteStop

/***
 *  Mapper class that transforms RemoteLine instance into a DBLine instance
 */
class RemoteToDBLineMapper (val transport: Transport): Mapper<RemoteLine, DBFullLine>{
    private val stopsMapper = ListMapperImpl(RemoteToDBStopMapper())
    private val routeMapper = ListMapperImpl(RemoteToDBRouteMapper())

    override fun map(input: RemoteLine): DBFullLine {
        return DBFullLine(
            DBLine(code = input.code,
                    agencyId = input.agencyId,
                    transportName = transport.name,
                    name = input.name,
                    direction = input.direction,
                    isNightLine = input.isNightLine),
            stopsMapper.map(input.stops),
            routeMapper.map(input.route)
        )
    }
}

/***
 *  Mapper class that transforms RemoteStop instance into DBStop
 */
class RemoteToDBStopMapper: Mapper<RemoteStop, DBStop> {
    private val scheduleMapper = RemoteToDBScheduleMapper()
    private val locationMapper = RemoteToDBLocationMapper()

    override fun map(input: RemoteStop): DBStop {
        return  DBStop(
            code = input.code,
            name = input.name,
            connections = input.connections,
            location = locationMapper.map(input.location),
            schedule = scheduleMapper.map(input.schedule)
        )
    }
}

/***
 *  Mapper class that transforms RemoteLocation objects into DBLocation
 */
class RemoteToDBLocationMapper: Mapper<RemoteLocation, DBLocation> {
    override fun map(input: RemoteLocation): DBLocation {
        return  DBLocation(
            lat = input.lat,
            long = input.long
        )
    }
}


/***
 *  Mapper class that transforms RemoteSchedule instance into DBSchedule
 */
class RemoteToDBScheduleMapper: Mapper<RemoteSchedule, DBSchedule> {
    override fun map(input: RemoteSchedule): DBSchedule {
        return  DBSchedule(
            workingDays = input.workingDays,
            sunday = input.sunday,
            saturday = input.saturday,
            friday = null,
            monday2Tuesday = null)
    }
}

/***
 *  Mapper class that transforms RemoteLocation objects into DBLocation
 */
class RemoteToDBRouteMapper: Mapper<RemoteLocation, DBRouteLocation> {
    override fun map(input: RemoteLocation): DBRouteLocation {
        return  DBRouteLocation(
            location = DBLocation(input.lat, input.long)
        )
    }
}