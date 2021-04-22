package com.quoders.apps.qmoves.di

import com.quoders.apps.qmoves.realTime.BusRealTimeService
import com.quoders.apps.qmoves.realTime.RealTimeService
import dagger.Binds
import dagger.Module

// Module to make Dagger provide the realtimeService dependency
@Module
abstract class RealTimeServiceModule {

    // The only realtimeService available for now is BusRealTimeService.
    @Binds
    abstract fun provideRealTimeService(storage: BusRealTimeService): RealTimeService
}