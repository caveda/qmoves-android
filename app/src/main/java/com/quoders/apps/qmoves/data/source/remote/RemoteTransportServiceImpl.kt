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

    override suspend fun fetchLines(transport: Transport): Result<List<RemoteLine>> {
        TODO("Implementation pending")
    }

    private fun readFirebaseConfig(context: Context): FirebaseClientConfig {
        return FirebaseClientConfig(
                funcUrl = context.getString(R.string.firebase_func_url),
                funcHeaderValue = context.getString(R.string.firebase_func_header_value),
                storageMetadataPath = context.getString(R.string.firebase_storage_metadata),
                storageDataPath = context.getString(R.string.firebase_storage_data)
            )
    }
}