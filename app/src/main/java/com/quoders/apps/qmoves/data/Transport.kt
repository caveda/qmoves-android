package com.quoders.apps.qmoves.data

import android.os.Parcelable
import com.quoders.apps.qmoves.data.source.FakeTransportRepository
import com.quoders.apps.qmoves.data.source.TransportRepository
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize

/**
 *  Entity that represents a transport agency,e.g: bus, subway, tram, etc.
 */
@Parcelize
data class Transport (val name: String): Parcelable {

    // TODO Replace this by DI injection
    @IgnoredOnParcel
    val repository: TransportRepository = FakeTransportRepository()
}