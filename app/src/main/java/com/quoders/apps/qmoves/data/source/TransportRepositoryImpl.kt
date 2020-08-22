package com.quoders.apps.qmoves.data.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.quoders.apps.qmoves.data.*
import com.quoders.apps.qmoves.data.Result.Success
import com.quoders.apps.qmoves.data.mapper.*
import com.quoders.apps.qmoves.data.source.local.TransportDatabaseDao
import com.quoders.apps.qmoves.data.source.remote.RemoteLine
import com.quoders.apps.qmoves.data.source.remote.RemoteTransport
import com.quoders.apps.qmoves.data.source.remote.RemoteTransportService
import timber.log.Timber

class TransportRepositoryImpl (
    private val dbSource: TransportDatabaseDao,
    private val remoteSource: RemoteTransportService): TransportRepository {

    private var checkRemoteTransportsDone: Boolean = false
    private val transportControlUpdatesSet = mutableSetOf<String> ()

    override suspend fun getTransports(): Result<List<Transport>> {
        checkRemoteTransports()

        val result = dbSource.getTransports()
        val mapper = ListMapperImpl(DBTransportMapper())
        val transports = mapper.map(result)
        resetTransportControlUpdates()
        return Result.Success(transports)
    }

    private fun resetTransportControlUpdates() {
         transportControlUpdatesSet.clear()
    }

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

    override suspend fun addFavorite(favorite: Favorite) {
        val mapper = FavoriteMapper()
        dbSource.insertFavorite(mapper.map(favorite))
    }

    override suspend fun getAllFavorites(): Result<List<Favorite>> {
        val queryResult = dbSource.getFavorites()
        val retList = queryResult.map {
            val transport = DBTransportMapper().map(dbSource.getTransport(it.transportName))
            val line = DBLineMapper().map(dbSource.getLineOfAgency(it.transportName, it.lineCode))
            val stop = line.stops.first { s:Stop -> it.lineCode == s.code}
            Favorite(transport,line,stop)
        }
        return Result.Success(retList)
    }

    private suspend fun checkRemoteUpdates(transport: Transport) {
        if (transport.name in transportControlUpdatesSet) {
            Timber.i("checkRemoteUpdates: Remote updates for $transport already checked")
            return
        }

        val newDataAvailable = remoteSource.isNewDataAvailable(transport)
        if (newDataAvailable is Success && !newDataAvailable.data) {
            Timber.i("checkRemoteUpdates: No new data in remote source for $transport")
            transportControlUpdatesSet.add(transport.name)
            return
        }
        if (newDataAvailable is Error) {
            Timber.w("checkRemoteUpdates: Error checking for remote updates for $transport: $newDataAvailable")
            throw Exception(newDataAvailable)
        }

        val lines = remoteSource.fetchLines(transport)
        if (lines is Success && lines.data.isNotEmpty()) {
            Timber.i("checkRemoteUpdates: ${lines.data.size} $transport lines fetched")
            updateDbWithLinesData(transport, lines.data)
            transportControlUpdatesSet.add(transport.name)
        }
        else {
            Timber.w("checkRemoteUpdates: Error fetching for $transport remote data: $lines")
            throw Exception("Error fetching lines or zero lines fetched")
        }
    }

    private suspend fun checkRemoteTransports() {
        if (checkRemoteTransportsDone) {
            Timber.i("checkRemoteTransports: Remote transports list already checked")
            return
        }

        val remoteTransports = remoteSource.fetchTransports()
        if (remoteTransports is Success && remoteTransports.data.isNotEmpty()) {
            Timber.i("checkRemoteTransports: ${remoteTransports.data.size} transports found!")
            updateDbWithTransportsData(remoteTransports.data)
            checkRemoteTransportsDone=true
            return
        }

        if (remoteTransports is Result.Error){
            Timber.w("checkRemoteTransports: Error checking for remote transports")
            throw remoteTransports.exception
        }
    }

    private suspend fun updateDbWithTransportsData(transports: List<RemoteTransport>) {
        val mapper = ListMapperImpl(RemoteToDBTransportMapper())
        val dbTransports = mapper.map(transports)
        dbSource.insertUpdateTransports(dbTransports)
    }

    private suspend fun updateDbWithLinesData(transport: Transport, lines: List<RemoteLine>) {
        val mapper = ListMapperImpl(RemoteToDBLineMapper(transport))
        val dbLines = mapper.map(lines)
        dbSource.insertUpdateLines(transport.name, dbLines)
    }
}