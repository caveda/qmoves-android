package com.quoders.apps.qmoves.realTime

import com.quoders.apps.qmoves.data.Result
import com.quoders.apps.qmoves.data.Stop
import com.quoders.apps.qmoves.data.source.remote.RemoteLine
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.io.File
import java.io.InputStream
import java.lang.reflect.Type

/**
 * Real time service client for Bus
 */
class BusRealTimeService: RealTimeService {

    private var serviceConfig: BusServiceConfig? = null
    private val isServiceAvailable = serviceConfig!=null

    // initializer block
    init {
        loadServiceProperties()
    }

    private fun loadServiceProperties() {
        val serviceInfoContent = BusRealTimeService::class.java.getResource("/quoders-services.json")?.readText()
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        val adapter: JsonAdapter<BusServiceConfig> = moshi.adapter(BusServiceConfig::class.java)
        serviceConfig = adapter.fromJson(serviceInfoContent)
    }

    override fun getStopNextTransports(stop: Stop): List<StopNextTransports> {
        TODO("Not yet implemented")
    }
}