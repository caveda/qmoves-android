package com.quoders.apps.qmoves.data.source.remote

import com.quoders.apps.qmoves.data.Result

/***
 * Interface of the remote service that provides transport information.
 */
interface RemoteTransportService {

    suspend fun fetchLines(): Result<List<RemoteLine>>
}