package com.quoders.apps.qmoves.data.source

import com.quoders.apps.qmoves.data.*
import com.quoders.apps.qmoves.data.Result.Success
import com.quoders.apps.qmoves.data.mapper.*
import com.quoders.apps.qmoves.data.source.local.TransportDatabaseDao
import com.quoders.apps.qmoves.data.source.remote.RemoteLine
import com.quoders.apps.qmoves.data.source.remote.RemoteTransportService
import timber.log.Timber

class TransportRepositoryImpl (
    private val dbSource: TransportDatabaseDao,
    private val remoteSource: RemoteTransportService): TransportRepository {

    var checkRemoteUpdatesDone: Boolean = false

    override suspend fun getLines(transport: Transport): Result<List<Line>> {
        checkRemoteUpdates(transport)

        val result = dbSource.getLines(transport.name)
        val mapper = ListMapperImpl(DBLineMapper())
        return Result.Success(mapper.map(result))
    }

    override suspend fun getLineStops(line: Line): Result<List<Stop>> {
        val queryResult = dbSource.getLineWithStops(line.uuid)
        val mapper = ListMapperImpl(DBStopMapper())
        return Result.Success(mapper.map(queryResult.stops))
    }

    override suspend fun getRoute(line: Line): Result<List<Location>> {
        val queryResult = dbSource.getLineWithRoute(line.uuid)
        val mapper = ListMapperImpl(DBRouteLocationMapper())
        return Result.Success(mapper.map(queryResult.route))
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
            updateDbWithRemoteData(transport, lines.data)
            checkRemoteUpdatesDone=true
        }
        else {
            Timber.w("checkRemoteUpdates: Error fetching for $transport remote data: $lines")
            throw Exception("Error fetching lines or zero lines fetched")
        }
    }

    private suspend fun updateDbWithRemoteData(transport: Transport, lines: List<RemoteLine>) {
        val mapper = ListMapperImpl(RemoteToDBLineMapper(transport))
        val dbLines = mapper.map(lines)
        dbSource.insertUpdateTransportData(transport.name, dbLines)
    }
}