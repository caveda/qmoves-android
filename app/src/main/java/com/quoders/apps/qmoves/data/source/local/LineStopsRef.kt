package com.quoders.apps.qmoves.data.source.local

import androidx.room.*

/***
 * Lines and stops has a many-to-many relation because a line has multiple stops whereas a stop
 * is shared by more than one line.
 */
@Entity(primaryKeys = ["lineId", "stopId"],
    indices = [Index(value = ["lineId", "stopId"], unique = true)])
data class LineStopsRef(
    val lineId: Long,
    val stopId: Long
)

data class LineWithStops(
    @Embedded val line: DBLine,
    @Relation(
        parentColumn = "lineId",
        entityColumn = "stopId",
        associateBy = Junction(LineStopsRef::class)
    )
    val stops: List<DBStop>
)