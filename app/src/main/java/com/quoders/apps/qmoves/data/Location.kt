package com.quoders.apps.qmoves.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 A GPS location expressed in terms of latitude and longitude
 */
@Parcelize
data class Location (val lat: Double, val long: Double): Parcelable