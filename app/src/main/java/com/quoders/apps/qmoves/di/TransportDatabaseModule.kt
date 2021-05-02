package com.quoders.apps.qmoves.di

import android.content.Context
import androidx.room.Room
import com.quoders.apps.qmoves.data.source.local.TransportDatabase
import com.quoders.apps.qmoves.data.source.local.TransportDatabaseDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object TransportDatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): TransportDatabase {
        return Room.databaseBuilder(
            appContext,
            TransportDatabase::class.java,
            "qmoves_transport_database"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideTransportDatabaseDao(database: TransportDatabase): TransportDatabaseDao {
        return database.transportDatabaseDao()
    }
}