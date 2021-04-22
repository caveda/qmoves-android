package com.quoders.apps.qmoves.di

import com.quoders.apps.qmoves.data.source.local.TransportDatabase
import com.quoders.apps.qmoves.realTime.BusRealTimeService
import com.quoders.apps.qmoves.realTime.RealTimeService
import dagger.Binds
import dagger.Module

// Module to make Dagger provide the transportDatabase dependency
@Module
abstract class TransportDatabaseModule {

    @Binds
    abstract fun provideTransportDatabase(database: TransportDatabase): TransportDatabase
}