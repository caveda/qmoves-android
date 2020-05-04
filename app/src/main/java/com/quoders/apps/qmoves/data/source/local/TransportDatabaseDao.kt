package com.quoders.apps.qmoves.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface TransportDatabaseDao {

    @Transaction
    suspend fun populateAgencyLines (agency: String, line : List<DBLine>) {

    }

    @Insert
    suspend fun insertAll(line : List<DBLine>)

    @Query("SELECT * FROM transport_line WHERE transportName=:agency")
    suspend fun getLines(agency : String): List<DBLine>

    @Transaction
    @Query("SELECT * FROM transport_line WHERE lineId=:lineId")
    suspend fun getLineWithStops(lineId: Long): List<LineWithStops>

    @Transaction
    @Query("SELECT * FROM transport_line WHERE lineId=:lineId")
    suspend fun getLineWithRoute(lineId: Long): List<LineWithRoute>
}