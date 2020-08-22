package com.quoders.apps.qmoves.data.mapper

import com.quoders.apps.qmoves.data.Favorite
import com.quoders.apps.qmoves.data.source.local.DBFavorite

/***
 *  Mapper class that transforms Favorite objects in DBFavorite object.
 */
class FavoriteMapper: Mapper<Favorite, DBFavorite>{

    override fun map(input: Favorite): DBFavorite {
        return  DBFavorite(
            lineCode = input.line.code,
            stopCode = input.stop.code,
            transportName = input.transport.name
        )
    }
}
