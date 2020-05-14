package com.quoders.apps.qmoves.data.mapper
import com.quoders.apps.qmoves.data.Transport
import com.quoders.apps.qmoves.data.source.local.DBTransport

/***
 *  Mapper class that transforms DBTransport objects in domain entity Transport
 */
class DBTransportMapper: Mapper<DBTransport, Transport>{

    override fun map(input: DBTransport): Transport {
        return  Transport(
            name = input.name,
            color = input.color,
            metadataPath = input.pathMetadata,
            dataPath = input.pathData,
            type = input.type,
            newsFeed = input.newsFeed)
    }
}
