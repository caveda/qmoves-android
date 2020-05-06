package com.quoders.apps.qmoves.data.source

import com.quoders.apps.qmoves.data.Line
import com.quoders.apps.qmoves.data.Result
import com.quoders.apps.qmoves.data.Stop
import com.quoders.apps.qmoves.data.Transport

/**
 Interface to query transport information
 */
interface TransportRepository {

    /**
     * Returns the complete list of lines.
     */
    suspend fun getLines(agency: Transport): Result<List<Line>>

    /**
     * Returns the list of stops of the given line
     */
    suspend fun getLineStops(line: Line): Result<List<Stop>>

}