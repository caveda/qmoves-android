package com.quoders.apps.qmoves.data.source.local

import androidx.room.Embedded
import androidx.room.Relation
import com.quoders.apps.qmoves.data.Line
import com.quoders.apps.qmoves.data.Transport

data class TransportLines(
    @Embedded val transport: Transport,
    @Relation(
        parentColumn = "transportId",
        entityColumn = "transportOfLineId"
    )
    val lines: List<Line>
)