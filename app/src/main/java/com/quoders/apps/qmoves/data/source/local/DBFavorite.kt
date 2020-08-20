package com.quoders.apps.qmoves.data.source.local

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * DTO object representing a favorite stop. A favorite can be uniquely identified by
 * the tuple: transport, line, stop.
 */
@Entity(tableName = "favorite")
data class DBFavorite (
    @PrimaryKey(autoGenerate = true)
    val lineCode: String,
    var stopCode: String,
    var transportName: String)
