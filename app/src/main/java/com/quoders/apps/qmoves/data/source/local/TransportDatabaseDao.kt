package com.quoders.apps.qmoves.data.source.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface TransportDatabaseDao {

    @Transaction
    @Query("SELECT * FROM transport_agency WHERE transportId=:transportId")
    fun getTransportLines(transportId : Long): List<TransportLines>

    @Transaction
    @Query("SELECT * FROM transport_line WHERE lineId=:lineId")
    fun getLineWithStops(lineId: Long): List<LineStops>

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