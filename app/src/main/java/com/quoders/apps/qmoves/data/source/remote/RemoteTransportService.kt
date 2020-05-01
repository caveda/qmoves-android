package com.quoders.apps.qmoves.data.source.remote

import com.quoders.apps.qmoves.data.Result
import com.quoders.apps.qmoves.data.Transport

/***
 * Interface of the remote service that provides transport information.
 */
interface RemoteTransportService {

    /***
     * Fetch all lines of the given transport.
     */
    suspend fun fetchLines(transport: Transport): Result<List<RemoteLine>>
}