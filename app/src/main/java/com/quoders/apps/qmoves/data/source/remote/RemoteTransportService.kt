package com.quoders.apps.qmoves.data.source.remote

import com.quoders.apps.qmoves.data.Result
import com.quoders.apps.qmoves.data.Transport
import dagger.Component
import javax.inject.Singleton

/***
 * Interface of the remote service that provides transport information.
 */
interface RemoteTransportService {

    /***
     * Checks whether there is new data available for the given transport.
     */
    suspend fun isNewDataAvailable (transport: Transport): Result<Boolean>

    /***
     * Fetch all lines of the given transport.
     */
    suspend fun fetchLines(transport: Transport): Result<List<RemoteLine>>

    /***
     * Fetch transports list
     */
    suspend fun fetchTransports(): Result<List<RemoteTransport>>
}