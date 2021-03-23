package com.quoders.apps.qmoves.realTime

import com.quoders.apps.qmoves.data.Stop

/**
 * Interface to interact with the Realtime service
 */
interface RealTimeService {
    /**
     * Given a stop, returns the list of next transports to arrive at the stop.
     */
    suspend fun getStopNextTransports(stop: Stop): List<StopNextTransports>
}

/**
 * Entity containing the information of the
 * next transports for a stop.
 */
data class StopNextTransports(
    val lineId: String,
    val stopId: String,
    val arrivalTime: String)