package com.quoders.apps.qmoves.data.source.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.quoders.apps.qmoves.data.Line

/**
Database DTO representing a Line entity
 */
@Entity(tableName = "transport_line")
data class DBLine (
    @PrimaryKey(autoGenerate = true)
    val lineId: Long = 0L,
    val transportName: String,
    val code: String,
    val agencyId: String,
    val name: String,
    val direction: Line.Direction,
    val isNightLine: Boolean)