package com.quoders.apps.qmoves.realTime.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Entity containing the information of the
 * next transports for a stop.
 */
@Parcelize
data class StopNextTransport(
    val lineId: String,
    val stopId: String,
    val arrivalTimeEpochSeconds: Long,
    val minutesToArrival: String): Parcelable