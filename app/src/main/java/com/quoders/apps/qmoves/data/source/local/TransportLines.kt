package com.quoders.apps.qmoves.data.source.local

import androidx.room.Embedded
import androidx.room.Relation

data class TransportLines(
    @Embedded val transport: DBTransport,
    @Relation(
        parentColumn = "transportId",
        entityColumn = "transportOfLineId"
    )
    val lines: List<DBLine>
)