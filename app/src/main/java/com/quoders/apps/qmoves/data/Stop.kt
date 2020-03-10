package com.quoders.apps.qmoves.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
  Entity representing a stop of a Line
 */
@Parcelize
data class Stop (
    val id: String,
    val name: String,
    val schedule: Schedule,
    val location: Location
): Parcelable

@Parcelize
data class Schedule (
    val workingDays: String,
    val monday2Tuesday: String,
    val friday: String,
    val saturday: String,
    val sunday: String
): Parcelable
