package com.quoders.apps.qmoves.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize

/**
 Data class representing a line of a transit
 */
@Entity(tableName = "transport_line")
@Parcelize
data class Line (
    @PrimaryKey(autoGenerate = true)
    var lineId: Long = 0L,
    var transportOfLineId: Long = 0L,
    @Json(name = "Id") var code: String,
    @Json(name = "AgencyId") var agencyId: String,
    @Json(name = "Name") var name: String,
    @Json(name = "Dir") var direction: Direction = Direction.FORWARD,
    @Json(name = "Night") var isNightLine: Boolean,
    @Ignore // Ignored by database
    @Json(name = "Stops") var stops: MutableList<Stop> = mutableListOf<Stop>(),

    @Ignore // Ignored by database
    @Json(name = "Map") var route: MutableList<Location> = mutableListOf<Location>())
    : Parcelable {

    constructor() : this(0,0,"","","",Direction.BACKWARD,false)

    @Ignore
    @IgnoredOnParcel
    val uniqueId : String = agencyId + direction.code

    @Ignore
    @IgnoredOnParcel
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