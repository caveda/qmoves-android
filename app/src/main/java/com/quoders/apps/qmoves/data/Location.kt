package com.quoders.apps.qmoves.data

import android.os.Parcelable
import com.google.android.gms.maps.model.LatLng
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

/**
 A GPS location expressed in terms of latitude and longitude
 */
@Parcelize
data class Location (
    @Json(name = "La") var lat: Double,
    @Json(name = "Lo") var long: Double )
    : Parcelable {

    constructor() : this(.0,.0)

    fun toLatLng():LatLng = LatLng(this.lat, this.long)
}