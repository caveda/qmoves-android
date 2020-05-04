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
    val stopId: Long = 0L,
    val code: String,
    val name: String,
    @Embedded val schedule: DBSchedule,
    @Embedded val location: DBLocation,
    val connections: String? = null)

data class DBSchedule (
    val workingDays: String?,
    val monday2Tuesday: String?,
    val friday: String?,
    val saturday: String?,
    val sunday: String?)

/**
 * Database DTO representing a Location
 */
data class DBLocation (
    var lat: Double = .0,
    var long: Double = .0)
