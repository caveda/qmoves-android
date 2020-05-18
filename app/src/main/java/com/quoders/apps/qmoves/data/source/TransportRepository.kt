package com.quoders.apps.qmoves.data.source

import com.quoders.apps.qmoves.data.*

/**
 Interface to query transport information
 */
interface TransportRepository {

    /**
     * Returns the list of available transports
     */
    suspend fun getTransports(): Result<List<Transport>>

    /**
     * Returns the complete list of lines.
     */
    suspend fun getLines(agency: Transport): Result<List<Line>>

    /**
     * Returns the list of stops of the given line
     */
    suspend fun getLineStops(line: Line): Result<List<Stop>>

    /**
     * Returns the route of a line
     */
    suspend fun getRoute(line: Line): Result<List<Location>>
}