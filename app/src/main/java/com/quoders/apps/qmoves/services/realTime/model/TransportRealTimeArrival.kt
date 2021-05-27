package com.quoders.apps.qmoves.services.realTime.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Entity containing the realtime arrival time
 * for a given line and stop.
 */
@Parcelize
data class TransportRealTimeArrival(
    val lineId: String,
    val stopId: String,
    val arrivalTimeEpochSeconds: Long): Parcelable