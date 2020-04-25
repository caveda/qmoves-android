package com.quoders.apps.qmoves.data.source.local

import androidx.room.TypeConverter
import com.quoders.apps.qmoves.data.Line

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
}