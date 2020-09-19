package com.quoders.apps.qmoves.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
Data class representing a the domain entity favorite stop.
 */
@Parcelize
data class Favorite (
    var transport: Transport,
    var line: Line,
    var stop: Stop
): Parcelable