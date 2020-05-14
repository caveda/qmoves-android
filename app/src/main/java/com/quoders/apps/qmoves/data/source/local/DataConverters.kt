package com.quoders.apps.qmoves.data.source.local

import androidx.room.TypeConverter
import com.quoders.apps.qmoves.data.Line
import com.quoders.apps.qmoves.data.Transport

class DataConverters {
    @TypeConverter
    fun fromDirection(value: String?): Line.Direction? {
        return value?.let {
            if (it==Line.Direction.BACKWARD.name) {
                Line.Direction.BACKWARD
            }
            else {
                Line.Direction.FORWARD
            }
        }
    }

    @TypeConverter
    fun stringToDirection(direction: Line.Direction?): String? {
        return direction?.name
    }

    @TypeConverter
    fun stringToTransportType(value: String?): Transport.TransportType? {
        return value?.let {
            when (it) {
                Transport.TransportType.BUS.name -> Transport.TransportType.BUS
                Transport.TransportType.TRAM.name -> Transport.TransportType.TRAM
                Transport.TransportType.SUBWAY.name -> Transport.TransportType.SUBWAY
                else -> throw Exception("Unknown transport $it")
            }
        }
    }

    @TypeConverter
    fun transportTypeToString(type: Transport.TransportType?): String? {
        return type?.name
    }
}