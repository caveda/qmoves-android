package com.quoders.apps.qmoves.data.source.local

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * DTO object representing a favorite stop. A favorite can be uniquely identified by
 * the tuple: transport, line, stop.
 */
@Entity(tableName = "favorite", primaryKeys = ["lineAgencyId", "stopCode", "transportName"])
data class DBFavorite (
    @NonNull
    val lineAgencyId: String,
    @NonNull
    var stopCode: String,
    @NonNull
    var transportName: String)
