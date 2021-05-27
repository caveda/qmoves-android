package com.quoders.apps.qmoves.services.realTime

import com.quoders.apps.qmoves.data.Stop
import com.quoders.apps.qmoves.services.realTime.model.TransportRealTimeArrival
import com.quoders.apps.qmoves.data.*

/**
 * Interface to interact with the Realtime service
 */
interface RealTimeService {
    /**
     * Given a stop, returns the list of next transports to arrive at the stop.
     */
    suspend fun getStopNextTransports(stop: Stop): Result<List<TransportRealTimeArrival>>
}

