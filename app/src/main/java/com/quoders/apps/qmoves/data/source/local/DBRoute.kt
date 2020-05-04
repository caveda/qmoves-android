package com.quoders.apps.qmoves.data.source.local

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

/**
 * Database DTO representing a route
 */
@Entity(tableName = "line_route")
data class DBRouteLocation (
    @PrimaryKey(autoGenerate = true)
    val locationId: Long = 0L,
    val routeLineId: Long,
    @Embedded val location: DBLocation)


data class LineWithRoute (
    @Embedded val line: DBLine,
    @Relation(
        parentColumn = "lineId",
        entity = DBRouteLocation::class,
        entityColumn = "routeLineId")
    val route: List<DBRouteLocation>
    )