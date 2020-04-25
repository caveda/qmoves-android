package com.quoders.apps.qmoves.data.source.local

import android.content.Context
import androidx.databinding.adapters.Converters
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.quoders.apps.qmoves.data.Line
import com.quoders.apps.qmoves.data.Location
import com.quoders.apps.qmoves.data.Stop
import com.quoders.apps.qmoves.data.Transport

@Database(entities = [Transport::class, Line::class, Stop::class, Location::class,
    LineStopsRef::class], version = 1,  exportSchema = false)
@TypeConverters(DataConverters::class)
abstract class TransportDatabase : RoomDatabase() {

    abstract val transportDatabaseDao: TransportDatabaseDao

    companion object {
        @Volatile
        private var INSTANCE: TransportDatabase? = null

        fun getInstance(context: Context): TransportDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        TransportDatabase::class.java,
                        "transport_qmoves_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }

}