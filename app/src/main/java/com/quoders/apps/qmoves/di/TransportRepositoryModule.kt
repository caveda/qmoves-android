package com.quoders.apps.qmoves.di

import android.content.Context
import androidx.room.Room
import com.quoders.apps.qmoves.data.source.TransportRepository
import com.quoders.apps.qmoves.data.source.TransportRepositoryImpl
import com.quoders.apps.qmoves.data.source.local.TransportDatabase
import com.quoders.apps.qmoves.data.source.local.TransportDatabaseDao
import com.quoders.apps.qmoves.data.source.remote.RemoteTransportService
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class TransportRepositoryModule {

    @Singleton
    @Binds
    abstract fun bindTransportRepository(repository: TransportRepositoryImpl): TransportRepository
}
