package com.quoders.apps.qmoves.data.source.local

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.Relation
import com.quoders.apps.qmoves.data.Line
import com.quoders.apps.qmoves.data.Stop

@Entity(primaryKeys = ["lineId", "stopId"])
data class LineStopsRef(
    val lineId: Long,
    val stopId: Long
)


data class LineStops(
    @Embedded val line: Line,
    @Relation(
        parentColumn = "lineId",
        entityColumn = "stopId",
        associateBy = Junction(LineStopsRef::class)
    )
    val stops: List<Stop>
)