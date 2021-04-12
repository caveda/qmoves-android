package com.quoders.apps.qmoves.stopDetail.next

import android.os.Parcelable
import com.quoders.apps.qmoves.data.DataLoadingStatus
import kotlinx.android.parcel.Parcelize


/**
 * Entity containing the UI information of the next transport
 * coming to a stop.
 */
@Parcelize
data class StopNextLineTransport(
    val lineId: String,
    val lineName: String,
    val stopId: String,
    val realtimeQueryInProgress: Boolean,
    val realtimeLoadingStatus: DataLoadingStatus,
    val minutesToArrival: String): Parcelable