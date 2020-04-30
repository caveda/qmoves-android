package com.quoders.apps.qmoves.data.source

import com.quoders.apps.qmoves.data.Line
import com.quoders.apps.qmoves.data.Result
import com.quoders.apps.qmoves.data.Transport
import com.quoders.apps.qmoves.data.mapper.DBLineMapper
import com.quoders.apps.qmoves.data.mapper.ListMapperImpl
import com.quoders.apps.qmoves.data.source.local.TransportDatabaseDao
import com.quoders.apps.qmoves.data.source.remote.RemoteTransportService

class TransportRepositoryImpl (val dbSource: TransportDatabaseDao,
                               val remoteSource: RemoteTransportService): TransportRepository {

    override suspend fun getLines(agency: Transport): Result<List<Line>> {
        val result = dbSource.getLines(agency.name)
        val mapper = ListMapperImpl(DBLineMapper())
        return Result.Success(mapper.map(result))
    }
}