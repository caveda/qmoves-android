package com.quoders.apps.qmoves.data.source.local

import androidx.room.*

/**
 * DTO object representing a Stop. Line has a one-to-many relation with Stop.
 */
@Entity(tableName = "line_stop",
        foreignKeys = [ForeignKey(entity = DBLine::class, parentColumns = arrayOf("lineId"),
                    childColumns = arrayOf("stopLineId"), onDelete = ForeignKey.CASCADE)])
data class DBStop (
    @PrimaryKey(autoGenerate = true)
    val stopId: Long = 0L,
    var stopLineId: Long = 0L,
    val code: String,
    val name: String,
    @Embedded val schedule: DBSchedule,
    @Embedded val location: DBLocation,
    val connections: String? = null)

/***
 * Data class that supports the one-to-many relation
 */
data class LineWithStops(
    @Embedded val line: DBLine,
    @Relation(
        parentColumn = "lineId",
        entityColumn = "stopLineId"
    )
    val stops: List<DBStop>
)

/***
 * Schedule of a stop for a given line
 */
data class DBSchedule (
    val workingDays: String?,
    val monday2Tuesday: String?,
    val friday: String?,
    val saturday: String?,
    val sunday: String?)

/**
 * Database DTO representing a Location
 */
data class DBLocation (
    var lat: Double = .0,
    var lng: Double = .0)
