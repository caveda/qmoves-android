package com.quoders.apps.qmoves.data.source.remote

import com.squareup.moshi.Json

/***
 * DTO class representing a Transport object returned from remote source.
 */
data class RemoteTransport (
    @Json(name = "name")
    val name: String,
    @Json(name = "type")
    val type: String,
    @Json(name = "color")
    val color: String,
    @Json(name = "path_metadata")
    val storageMetadata: String,
    @Json(name = "path_data")
    val storageData: String,
    @Json(name = "news")
    val newsFeed: String
)