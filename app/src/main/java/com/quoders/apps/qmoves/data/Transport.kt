package com.quoders.apps.qmoves.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 *  Entity that represents a transport agency,e.g: bus, subway, tram, etc.
 */
@Parcelize
data class Transport (
    val name: String
): Parcelable {

    override fun toString(): String {
        return name
    }
}