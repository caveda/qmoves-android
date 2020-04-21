package com.quoders.apps.qmoves.data

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

/**
  Entity representing a stop of a Line
 */
@Parcelize
//@JsonClass(generateAdapter = true)
data class Stop (
    @Json(name = "Id") val id: String,
    @Json(name = "Na") val name: String,
    @Json(name = "Sc") val schedule: Schedule,
    @Json(name = "Lc") val location: Location,
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
