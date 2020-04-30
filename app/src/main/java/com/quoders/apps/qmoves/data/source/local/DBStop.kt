package com.quoders.apps.qmoves.data.source.local

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * DTO object representing a Stop
 */
@Entity(tableName = "line_stop")
data class DBStop (
    @PrimaryKey(autoGenerate = true)
    var stopId: Long = 0L,
    var code: String,
    var name: String,
    @Embedded var schedule: DBSchedule,
    @Embedded var location: DBLocation,
    var connections: String? = null)

data class DBSchedule (
    var workingDays: String?,
    var monday2Tuesday: String?,
    var friday: String?,
    var saturday: String?,
    var sunday: String?)