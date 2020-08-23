package com.quoders.apps.qmoves.data.source

import com.quoders.apps.qmoves.data.*

/**
 Interface to query transport information
 */
interface TransportRepository {

    /**
     * Returns the list of available transports
     */
    suspend fun getTransports(): Result<List<Transport>>

    /**
     * Returns the complete list of lines.
     */
    suspend fun getLines(agency: Transport): Result<List<Line>>

    /**
     * Returns the list of stops of the given line
     */
    suspend fun getLineStops(line: Line): Result<List<Stop>>

    /**
     * Returns the route of a line
     */
    suspend fun getRoute(line: Line): Result<List<Location>>

    /**
     * Add the favorite to the repository. Typically a favorite is a stop of a line.
     */
    suspend fun addFavorite(favorite: Favorite)

    /**
     * Deletes the favorite from the repository.
     */
    suspend fun removeFavorite(favorite: Favorite)

    /**
     * Returns the list of all favorites.
     */
    suspend fun getAllFavorites(): Result<List<Favorite>>
}