package com.quoders.apps.qmoves.di

import com.quoders.apps.qmoves.data.source.remote.RemoteTransportService
import com.quoders.apps.qmoves.data.source.remote.RemoteTransportServiceImpl
import com.quoders.apps.qmoves.realTime.BusRealTimeService
import com.quoders.apps.qmoves.realTime.RealTimeService
import dagger.Binds
import dagger.Module

// Module to make Dagger provide the RemoteTransportService dependency
@Module
abstract class RemoteTransportServiceModule {

    @Binds
    abstract fun provideRemoteTransportService(repository: RemoteTransportServiceImpl): RemoteTransportService
}