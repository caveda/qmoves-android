package com.quoders.apps.qmoves.data.source.local

import androidx.room.*
import timber.log.Timber

@Dao
interface TransportDatabaseDao {

    @Transaction
    suspend fun insertUpdateLines (transport: String, lines: List<DBFullLine>) {
        val deletedItems = deleteAllTransportLines(transport)
        Timber.d("insertUpdateTransportData: Deleted $deletedItems lines")
        lines.forEach{
            val lineId = insertLine(it.line)
            it.stops.forEach{ s -> s.stopLineId = lineId}
            insertStops(it.stops)
            it.route.forEach{ r -> r.routeLineId = lineId}
            insertRoute(it.route)
        }
    }

    @Transaction
    suspend fun insertUpdateTransports (data: List<DBTransport>) {
        val deletedItems = deleteAllTransports()
        Timber.d("insertUpdateTransports: Deleted $deletedItems transports")
        insertTransports(data)
    }

    @Query("DELETE FROM transport_line WHERE transportName=:transport")
    suspend fun deleteAllTransportLines(transport: String): Int

    @Query("DELETE FROM transport_agencies")
    suspend fun deleteAllTransports(): Int

    @Delete
    suspend fun deleteFavorite(favorite : DBFavorite)

    @Insert (onConflict = OnConflictStrategy.ABORT)
    suspend fun insertLine(lines : DBLine): Long

    @Insert (onConflict = OnConflictStrategy.ABORT)
    suspend fun insertStops(stops : List<DBStop>)

    @Insert (onConflict = OnConflictStrategy.ABORT)
    suspend fun insertRoute(route : List<DBRouteLocation>)

    @Insert (onConflict = OnConflictStrategy.ABORT)
    suspend fun insertTransports(transports : List<DBTransport>)

    @Insert (onConflict = OnConflictStrategy.ABORT)
    suspend fun insertFavorite(favorite : DBFavorite)

    @Query("SELECT * FROM transport_line WHERE transportName=:agency")
    suspend fun getLines(agency : String): List<DBLine>

    @Transaction
    @Query("SELECT * FROM transport_line WHERE lineId=:lineId")
    suspend fun getLineWithStops(lineId: Long): LineWithStops

    @Transaction
    @Query("SELECT * FROM transport_line WHERE lineId=:lineId")
    suspend fun getLineWithRoute(lineId: Long): LineWithRoute

    @Transaction
    @Query("SELECT * FROM transport_agencies")
    suspend fun getTransports(): List<DBTransport>

    @Transaction
    @Query("SELECT * FROM favorite")
    suspend fun getFavorites(): List<DBFavorite>
}