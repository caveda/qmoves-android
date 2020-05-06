package com.quoders.apps.qmoves.data.source.local

import androidx.room.*

/**
 * Database DTO representing a route
 */
@Entity(tableName = "line_route",
    foreignKeys = [ForeignKey(entity = DBLine::class, parentColumns = arrayOf("lineId"),
                    childColumns = arrayOf("routeLineId"), onDelete = ForeignKey.CASCADE)])
data class DBRouteLocation (
    @PrimaryKey(autoGenerate = true)
    val locationId: Long = 0L,
    var routeLineId: Long = 0L,
    @Embedded val location: DBLocation)


data class LineWithRoute (
    @Embedded val line: DBLine,
    @Relation(
        parentColumn = "lineId",
        entity = DBRouteLocation::class,
        entityColumn = "routeLineId")
    val route: List<DBRouteLocation>
    )