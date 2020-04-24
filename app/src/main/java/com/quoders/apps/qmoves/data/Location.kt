package com.quoders.apps.qmoves.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.android.gms.maps.model.LatLng
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

/**
 A GPS location expressed in terms of latitude and longitude
 */
@Entity(tableName = "location")
@Parcelize
data class Location (
    @PrimaryKey(autoGenerate = true)
    @Transient var locationId: Long = 0L,

    @Json(name = "La") val lat: Double,
    @Json(name = "Lo") val long: Double): Parcelable {

    fun toLatLng():LatLng = LatLng(this.lat, this.long)
}