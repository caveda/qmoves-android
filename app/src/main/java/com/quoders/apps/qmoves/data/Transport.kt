package com.quoders.apps.qmoves.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 *  Entity that represents a transport agency,e.g: bus, subway, tram, etc.
 */
@Parcelize
data class Transport (
    val name: String,
    val type: TransportType,
    val color: String,
    val metadataPath: String,
    val dataPath: String,
    val newsFeed: String
): Parcelable {

    override fun toString(): String {
        return name
    }

    enum class TransportType (val value: String){
        BUS("bus"),
        SUBWAY("subway"),
        TRAM("tram")
    }
}

