package com.quoders.apps.qmoves.data.source

import com.quoders.apps.qmoves.data.*
import com.quoders.apps.qmoves.data.Result.Success
import com.quoders.apps.qmoves.data.mapper.*
import com.quoders.apps.qmoves.data.source.local.DBStop
import com.quoders.apps.qmoves.data.source.local.TransportDatabase
import com.quoders.apps.qmoves.data.source.local.TransportDatabaseDao
import com.quoders.apps.qmoves.data.source.remote.RemoteLine
import com.quoders.apps.qmoves.data.source.remote.RemoteTransport
import com.quoders.apps.qmoves.data.source.remote.RemoteTransportService
import timber.log.Timber
import javax.inject.Inject

class TransportRepositoryImpl @Inject constructor(
    private val database: TransportDatabase,
    private val remoteSource: RemoteTransportService): TransportRepository {

    private var checkRemoteTransportsDone: Boolean = false
    private val transportControlUpdatesSet = mutableSetOf<String> ()

    private val dbSource = database.transportDatabaseDao

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

    override suspend fun getLineStops(transport: Transport, line: Line): Result<List<Stop>> {
        val queryResult = dbSource.getLineWithStops(line.uuid)
        val mapper = ListMapperImpl(DBStopMapper())
        val stops = mapper.map(queryResult.stops)
        return setFavoriteStops(transport, line, stops)
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

    override suspend fun removeFavorite(favorite: Favorite) {
        val mapper = FavoriteMapper()
        dbSource.deleteFavorite(mapper.map(favorite))
    }

    override suspend fun getAllFavorites(): Result<List<Favorite>> {
        val queryResult = dbSource.getFavorites()
        val retList = queryResult.map {
            val transport = DBTransportMapper().map(dbSource.getTransport(it.transportName))
            val lineWithStops = dbSource.getLineOfAgency(it.transportName, it.lineCode)
            val line = DBLineMapper().map(lineWithStops.line)
            val stop = lineWithStops.stops.first { s: DBStop -> it.stopCode == s.code}
            Favorite(transport, line, DBStopMapper().map(stop))
        }
        return Result.Success(retList)
    }

    override suspend fun getStopConnectionsLines(stop: Stop): Result<List<Line>> {
        var retList = listOf<Line>()

        stop.connections?.let {
            val codes = stop.connections!!.split(" ")
            val queryResult = dbSource.getLinesWithCodes(codes)
            retList = queryResult.map{dbLine ->
                DBLineMapper().map(dbLine)
            }
        }
        return Success(retList)
    }

    private suspend fun setFavoriteStops (transport: Transport, line: Line, stops: List<Stop>): Result<List<Stop>> {
        val queryResult = dbSource.getFavoritesOfLine(transport.name, line.code)
        stops.forEach { s ->
            queryResult.forEach { f ->
                if (s.code == f.stopCode) {
                    s.favorite = true
                }
            }
        }
        return Result.Success(stops)
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