package com.quoders.apps.qmoves.data.source

import com.quoders.apps.qmoves.data.Line
import com.quoders.apps.qmoves.data.Result
import com.quoders.apps.qmoves.data.Result.Success
import com.quoders.apps.qmoves.data.Transport
import com.quoders.apps.qmoves.data.mapper.DBLineMapper
import com.quoders.apps.qmoves.data.mapper.ListMapperImpl
import com.quoders.apps.qmoves.data.mapper.RemoteLineMapper
import com.quoders.apps.qmoves.data.source.local.TransportDatabaseDao
import com.quoders.apps.qmoves.data.source.remote.RemoteLine
import com.quoders.apps.qmoves.data.source.remote.RemoteTransportService
import timber.log.Timber

class TransportRepositoryImpl (val dbSource: TransportDatabaseDao,
                               val remoteSource: RemoteTransportService): TransportRepository {

    var checkRemoteUpdatesDone: Boolean = false

    override suspend fun getLines(transport: Transport): Result<List<Line>> {
        checkRemoteUpdates(transport)

        val result = dbSource.getLines(transport.name)
        val mapper = ListMapperImpl(DBLineMapper())
        return Result.Success(mapper.map(result))
    }


    private suspend fun checkRemoteUpdates(transport: Transport) {
        if (checkRemoteUpdatesDone) {
            Timber.i("checkRemoteUpdates: Remote updates for $transport already checked")
            return
        }

        val newDataAvailable = remoteSource.isNewDataAvailable(transport)
        if (newDataAvailable is Success && !newDataAvailable.data) {
            Timber.i("checkRemoteUpdates: No new data in remote source for $transport")
            checkRemoteUpdatesDone=true
            return
        }
        if (newDataAvailable is Error) {
            Timber.w("checkRemoteUpdates: Error checking for remote updates for $transport: $newDataAvailable")
            throw Exception(newDataAvailable)
        }

        val lines = remoteSource.fetchLines(transport)
        if (lines is Success && lines.data.isNotEmpty()) {
            Timber.i("checkRemoteUpdates: ${lines.data.size} $transport lines fetched")
            updateDbWithRemoteData(lines)
            checkRemoteUpdatesDone=true
        }
        else {
            Timber.w("checkRemoteUpdates: Error fetching for $transport remote data: $lines")
            throw Exception("Error fetching lines or zero lines fetched")
        }
    }

    private fun updateDbWithRemoteData(lines: Result.Success<List<RemoteLine>>) {
        val mapper = ListMapperImpl(RemoteLineMapper())

    }
}