package com.quoders.apps.qmoves.data.source.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.quoders.apps.qmoves.data.TransportAgency

@Dao
interface TransportDatabaseDao {

    @Transaction
    @Query("SELECT * FROM transport_agency")
    suspend fun getAgencyId(name: String): Long

    @Transaction
    @Query("SELECT * FROM transport_agency")
    suspend fun getAgencies(): List<TransportAgency>

    @Transaction
    @Query("SELECT * FROM transport_line WHERE transportName=:agency")
    suspend fun getLines(agency : String): List<DBLine>

    @Transaction
    @Query("SELECT * FROM transport_line WHERE lineId=:lineId")
    suspend fun getLineWithStops(lineId: Long): List<LineStops>

/*
    @Insert
    fun insert(night: SleepNight)

    @Update
    fun update (night: SleepNight)

    @Query("DELETE FROM daily_sleep_quality_table")
    fun clear ()

    @Query("SELECT * FROM daily_sleep_quality_table WHERE nightId=:key")
    fun get (key: Long): SleepNight

    @Query("SELECT * FROM daily_sleep_quality_table ORDER BY nightId DESC")
    fun getAllNights(): LiveData<List<SleepNight>>

    @Query("SELECT * FROM daily_sleep_quality_table ORDER BY nightId DESC LIMIT 1")
    fun getTonight(): SleepNight?
*/
}