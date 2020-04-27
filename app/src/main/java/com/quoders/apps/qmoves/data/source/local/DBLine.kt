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
    var lineId: Long = 0L,
    var transportOfLineId: Long = 0L,
    var code: String,
    var agencyId: String,
    var name: String,
    var direction: Line.Direction,
    var isNightLine: Boolean)