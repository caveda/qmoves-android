package com.quoders.apps.qmoves.data

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

/**
  Entity representing a stop of a Line
 */
@Parcelize
data class Stop (
    @Json(name = "Id") var code: String,
    @Json(name = "Na") var name: String,
    @Json(name = "Sc") var schedule: Schedule,
    @Json(name = "Lc") var location: Location,
    @Json(name = "Co") var connections: String? = null
): Parcelable

@Parcelize
data class Schedule (
    @Json(name = "Wor") var workingDays: String?,
    var monday2Tuesday: String?,
    var friday: String?,
    @Json(name = "Sat") var saturday: String?,
    @Json(name = "Sun") var sunday: String?
): Parcelable
