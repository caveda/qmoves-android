package com.quoders.apps.qmoves.data.source.remote

/**
 * Config data for Firebase client
 */
data class FirebaseClientConfig (
    val funcUrl: String,
    val funcHeaderValue: String,
    val storageMetadataPath: String,
    val storageDataPath: String
)