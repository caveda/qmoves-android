package com.quoders.apps.qmoves.data.source.local

import androidx.room.*

@Entity(primaryKeys = ["lineId", "locationId"],
    indices = [Index(value = ["lineId", "locationId"], unique = true)]
)
data class LineRouteRef(
    val lineId: Long,
    val locationId: Long
)

data class LineRoute (
    @Embedded
    val line: DBLine,
    @Relation(
        parentColumn = "lineId",
        entityColumn = "locationId",
        associateBy = Junction(LineRouteRef::class)
    )
    val route: List<DBLocation>
)