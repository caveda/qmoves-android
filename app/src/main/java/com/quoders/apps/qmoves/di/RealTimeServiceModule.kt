package com.quoders.apps.qmoves.di

import com.quoders.apps.qmoves.data.source.TransportRepository
import com.quoders.apps.qmoves.data.source.TransportRepositoryImpl
import com.quoders.apps.qmoves.realTime.BusRealTimeService
import com.quoders.apps.qmoves.realTime.RealTimeService
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
annotation class BusRealTime

@InstallIn(SingletonComponent::class)
@Module
abstract class RealtimeServiceModule {

    @BusRealTime
    @Singleton
    @Binds
    abstract fun bindRealTimeServiceModule(busRealTime: BusRealTimeService): RealTimeService
}
