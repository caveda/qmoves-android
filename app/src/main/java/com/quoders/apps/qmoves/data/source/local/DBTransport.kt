package com.quoders.apps.qmoves.data.source.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.quoders.apps.qmoves.data.Transport

@Entity(tableName = "transport_agencies")
data class DBTransport (
    @PrimaryKey(autoGenerate = true)
    var transportId: Long = 0L,
    val name: String,
    val color: String,
    val type: Transport.TransportType,
    val pathMetadata: String,
    val pathData: String,
    val newsFeed: String)