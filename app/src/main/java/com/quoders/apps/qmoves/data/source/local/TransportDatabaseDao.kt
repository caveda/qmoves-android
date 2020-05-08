package com.quoders.apps.qmoves.data.source.local

import androidx.room.*
import timber.log.Timber

@Dao
interface TransportDatabaseDao {

    @Transaction
    suspend fun insertUpdateTransportData (transport: String, data: List<DBFullLine>) {
        val deletedItems = deleteAllTransportLines(transport)
        Timber.d("insertUpdateTransportData: Deleted $deletedItems lines")
        data.forEach{
            val lineId = insertLine(it.line)
            it.stops.forEach{ s -> s.stopLineId = lineId}
            insertStops(it.stops)
            it.route.forEach{ r -> r.routeLineId = lineId}
            insertRoute(it.route)
        }
    }

    @Query("DELETE FROM transport_line WHERE transportName=:transport")
    suspend fun deleteAllTransportLines(transport: String): Int

    @Insert (onConflict = OnConflictStrategy.ABORT)
    suspend fun insertLine(lines : DBLine): Long

    @Insert (onConflict = OnConflictStrategy.ABORT)
    suspend fun insertStops(stops : List<DBStop>)

    @Insert (onConflict = OnConflictStrategy.ABORT)
    suspend fun insertRoute(route : List<DBRouteLocation>)

    @Query("SELECT * FROM transport_line WHERE transportName=:agency")
    suspend fun getLines(agency : String): List<DBLine>

    @Transaction
    @Query("SELECT * FROM transport_line WHERE lineId=:lineId")
    suspend fun getLineWithStops(lineId: Long): LineWithStops

    @Transaction
    @Query("SELECT * FROM transport_line WHERE lineId=:lineId")
    suspend fun getLineWithRoute(lineId: Long): LineWithRoute
}