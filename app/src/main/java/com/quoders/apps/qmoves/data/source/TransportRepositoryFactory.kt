package com.quoders.apps.qmoves.data.source

import android.content.Context
import com.quoders.apps.qmoves.data.source.local.TransportDatabase
import com.quoders.apps.qmoves.data.source.remote.RemoteTransportServiceImpl

/***
 * Factory that builds transport repository instances
 */
class TransportRepositoryFactory {

//    companion object {
//        @Volatile
//        private var INSTANCE: TransportRepository? = null
//
//        fun getInstance(context: Context): TransportRepository {
//            synchronized(this) {
//                var instance = INSTANCE
//
//                if (instance == null) {
//                    val database = TransportDatabase.getInstance(context)
//                    instance = TransportRepositoryImpl(
//                        database.transportDatabaseDao,
//                        RemoteTransportServiceImpl(context))
//
//                    INSTANCE = instance
//                }
//                return instance
//            }
//        }
//    }
}