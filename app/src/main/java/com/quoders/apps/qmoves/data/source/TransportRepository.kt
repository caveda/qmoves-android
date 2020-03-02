package com.quoders.apps.qmoves.data.source

import com.quoders.apps.qmoves.data.Line
import com.quoders.apps.qmoves.data.Result
/**
 Interface to obtain information of a particular transport
 */
interface TransportRepository {

    /**
     * Returns the complete list of lines including all associated data: stops, routes, etc.
     */
    suspend fun getLines(): Result<List<Line>>
}