package com.quoders.apps.qmoves.data.mapper
import com.quoders.apps.qmoves.data.source.local.DBTransport
import com.quoders.apps.qmoves.data.source.local.DataConverters
import com.quoders.apps.qmoves.data.source.remote.RemoteTransport

/***
 *  Mapper class that transforms DBTransport objects in domain entity Transport
 */
class RemoteToDBTransportMapper: Mapper<RemoteTransport,DBTransport>{

    override fun map(input: RemoteTransport): DBTransport {
        return  DBTransport(
            name = input.name,
            color = input.color,
            pathMetadata = input.storageMetadata,
            pathData = input.storageData,
            type = DataConverters().stringToTransportType(input.type)!!,
            newsFeed = input.newsFeed)
    }
}
