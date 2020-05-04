package com.quoders.apps.qmoves.data.source.local

import androidx.room.*

@Entity(primaryKeys = ["lineId", "stopId"],
    indices = [Index(value = ["lineId", "stopId"], unique = true)]
)
data class LineStopsRef(
    val lineId: Long,
    val stopId: Long
)

data class LinesWithStops(
    @Embedded val line: DBLine,
    @Relation(
        parentColumn = "lineId",
        entityColumn = "stopId",
        associateBy = Junction(LineStopsRef::class)
    )
    val stops: List<DBStop>
)