package com.quoders.apps.qmoves.data

import android.os.Parcelable
import com.google.android.gms.maps.model.LatLng
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

/**
 A GPS location expressed in terms of latitude and longitude
 */
@Parcelize
//@JsonClass(generateAdapter = true)
data class Location (
    @Json(name = "La") val lat: Double,
    @Json(name = "Lo") val long: Double): Parcelable {
  fun toLatLng():LatLng = LatLng(this.lat, this.long)
}