package com.quoders.apps.qmoves.di

import android.content.Context
import com.quoders.apps.qmoves.stopDetail.next.StopNextViewModel
import dagger.BindsInstance
import dagger.Component

@Component(modules = [RealTimeServiceModule::class,
                    RemoteTransportServiceModule::class,
                    TransportDatabaseModule::class,
                    TransportRepositoryModule::class])
interface AppComponent {

    // Factory to create instances of the AppComponent
    @Component.Factory
    interface Factory {
        // With @BindsInstance, the Context passed in will be available in the graph
        fun create(@BindsInstance context: Context): AppComponent
    }

    // Classes that can be injected by this Component
    fun inject(viewModel: StopNextViewModel)
}