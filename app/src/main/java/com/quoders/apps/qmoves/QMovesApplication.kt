package com.quoders.apps.qmoves

import android.app.Application
import timber.log.Timber
import timber.log.Timber.DebugTree

/**
 * An application for global initializations
 * */
class QMovesApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG)
            Timber.plant(DebugTree())
    }
}