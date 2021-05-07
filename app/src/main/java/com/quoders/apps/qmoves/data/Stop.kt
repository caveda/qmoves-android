package com.quoders.apps.qmoves.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
  Data class representing a the domain entity stop of a Line
 */
@Parcelize
data class Stop (
    var code: String,
    var name: String,
    var schedule: Schedule,
    var location: Location,
    var connections: String? = null,
    var favorite: Boolean = false
): Parcelable

