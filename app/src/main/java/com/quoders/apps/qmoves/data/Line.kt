package com.quoders.apps.qmoves.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

/**
 Data class representing a line of a transit
 */
@Entity(tableName = "transport_line")
@Parcelize
data class Line (
    @PrimaryKey(autoGenerate = true)
    @Transient var lineId: Long = 0L,
    @Transient val transportOfLineId: Long,

    @Json(name = "Id") val code: String,
    @Json(name = "AgencyId") val agencyId: String,
    @Json(name = "Name") val name: String,
    @Json(name = "Dir") val direction: Direction,
    @Json(name = "Night") val isNightLine: Boolean,

    @Ignore // Ignored by database
    @Json(name = "Stops") val stops: MutableList<Stop> = mutableListOf<Stop>(),

    @Ignore // Ignored by database
    @Json(name = "Map") val route: MutableList<Location> = mutableListOf<Location>())
    : Parcelable {

    @Ignore
    val uniqueId : String = agencyId + direction.code

    @Ignore
    val type : LineType = if (isNightLine) LineType.NIGHT else LineType.REGULAR

    enum class Direction (val direction: String, val code: String){
        FORWARD("FORWARD", "F"),
        BACKWARD("BACKWARD", "B")
    }

    enum class LineType {
        REGULAR,
        NIGHT
    }
}