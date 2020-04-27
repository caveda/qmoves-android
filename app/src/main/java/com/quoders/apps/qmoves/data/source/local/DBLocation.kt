package com.quoders.apps.qmoves.data.source.local

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Database DTO representing a Location
 */
@Entity(tableName = "location")
data class DBLocation (
    @PrimaryKey(autoGenerate = true)
    var locationId: Long = 0L,
    var lat: Double = .0,
    var long: Double = .0)
