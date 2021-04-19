package com.quoders.apps.qmoves.data.source.local

import android.content.Context
import com.quoders.apps.qmoves.data.source.TransportRepository
import com.quoders.apps.qmoves.data.source.TransportRepositoryFactory
import com.quoders.apps.qmoves.data.source.TransportRepositoryImpl
import com.quoders.apps.qmoves.data.source.remote.RemoteTransportServiceImpl
import javax.inject.Inject

class TransportDatabaseFactory @Inject constructor() {

    fun getInstance(context: Context): TransportDatabaseDao {
        return TransportDatabase.getInstance(context).transportDatabaseDao
    }
}