package com.quoders.apps.qmoves.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.quoders.apps.qmoves.data.source.FakeTransportRepository
import com.quoders.apps.qmoves.data.source.TransportRepository
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize

/**
 *  Entity that represents a transport agency,e.g: bus, subway, tram, etc.
 */
@Entity(tableName = "transport_agency")
@Parcelize
data class Transport (
    @PrimaryKey(autoGenerate = true)
    var transportId: Long = 0L,
    val name: String
): Parcelable {

    // TODO Replace this var by a proper DI injection
    @IgnoredOnParcel
    @Ignore
    var repository: TransportRepository = FakeTransportRepository()
}