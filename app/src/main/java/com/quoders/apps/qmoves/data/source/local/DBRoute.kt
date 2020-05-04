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
    var locationId: Long = 0L,
    var routeLineId: Long,
    var lat: Double = .0,
    var lon: Double = .0)


data class LineWithRoute (
    @Embedded var line: DBLine,
    @Relation(
        parentColumn = "lineId",
        entity = DBRouteLocation::class,
        entityColumn = "routeLineId")
    var route: List<DBRouteLocation>
    )