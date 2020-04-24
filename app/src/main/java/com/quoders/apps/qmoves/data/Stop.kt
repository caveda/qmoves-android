package com.quoders.apps.qmoves.data

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

/**
  Entity representing a stop of a Line
 */
@Parcelize
@Entity(tableName = "line_stop")
data class Stop (
    @PrimaryKey(autoGenerate = true)
    @Transient var stopId: Long = 0L,

    @Json(name = "Id") val code: String,
    @Json(name = "Na") val name: String,
    @Embedded @Json(name = "Sc") val schedule: Schedule,
    @Embedded @Json(name = "Lc") val location: Location,
    @Json(name = "Co") val connections: String?
): Parcelable

@Parcelize
data class Schedule (
    @Json(name = "Wor") val workingDays: String?,
    val monday2Tuesday: String?,
    val friday: String?,
    @Json(name = "Sat") val saturday: String?,
    @Json(name = "Sun") val sunday: String?
): Parcelable
