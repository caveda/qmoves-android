package com.quoders.apps.qmoves.realTime

import com.quoders.apps.qmoves.data.Stop
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import timber.log.Timber

/**
 * Real time service client for Bus
 */
class BusRealTimeService: RealTimeService {

    private var serviceConfig: RemoteServicesConfig? = null
    private val isServiceAvailable = serviceConfig!=null

    // initializer block
    init {
        loadServiceProperties()
    }

    private fun loadServiceProperties() {

        try{
            val serviceInfoContent = javaClass.getResource("/res/raw/quoders_services.json")?.readText()
            val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
            val adapter: JsonAdapter<RemoteServicesConfig> = moshi.adapter(RemoteServicesConfig::class.java)
            serviceConfig = adapter.fromJson(serviceInfoContent)
        }
        catch (ex: Exception)
        {
            Timber.e("can not load realtime service configuration: $ex")
        }
    }


    override fun getStopNextTransports(stop: Stop): List<StopNextTransports> {
        TODO("Not yet implemented")
    }
}