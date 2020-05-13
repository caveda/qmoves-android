package com.quoders.apps.qmoves.data.source.remote

import android.content.Context
import com.quoders.apps.qmoves.R
import com.quoders.apps.qmoves.data.Result
import com.quoders.apps.qmoves.data.Transport

/***
 *  Implementation of the client to fetch remote transport data.
 */
class RemoteTransportServiceImpl(context: Context): RemoteTransportService {

    private var firebaseClient: FirebaseClient? = null

    init {
        firebaseClient = FirebaseClient(readFirebaseConfig(context))
    }

    override suspend fun isNewDataAvailable (transport: Transport): Result<Boolean> {
        return firebaseClient!!.isNewDataAvailable(transport)
    }

    override suspend fun fetchLines(transport: Transport): Result<List<RemoteLine>> {
        return firebaseClient!!.fetchLines(transport)
    }

    private fun readFirebaseConfig(context: Context): FirebaseClientConfig {
        return FirebaseClientConfig(
                funcUrl = context.getString(R.string.firebase_func_url),
                funcHeaderValue = context.getString(R.string.firebase_func_header_value),
                storageTransportPath = context.getString(R.string.firebase_transports_file)
            )
    }
}