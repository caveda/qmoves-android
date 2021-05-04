package com.quoders.apps.qmoves.data.source.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [DBTransport::class, DBLine::class, DBStop::class, DBRouteLocation::class,
    DBFavorite::class],
    version = 3,  exportSchema = false)
@TypeConverters(DataConverters::class)
abstract class TransportDatabase : RoomDatabase() {

    abstract fun transportDatabaseDao(): TransportDatabaseDao


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