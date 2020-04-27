package com.quoders.apps.qmoves.data.source.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transport_agency")
data class DBTransport (
    @PrimaryKey(autoGenerate = true)
    var transportId: Long = 0L,
    val name: String)