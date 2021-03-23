package com.quoders.apps.qmoves.realTime

import com.quoders.apps.qmoves.data.Stop
import com.quoders.apps.qmoves.realTime.api.RealTimeServiceApi
import com.quoders.apps.qmoves.realTime.model.Arrivals
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.android.synthetic.main.fragment_stop_schedule.*
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import timber.log.Timber
import java.lang.IllegalStateException

/**
 * Real time service client for Bus
 */
class BusRealTimeService: RealTimeService {

    private var serviceConfig: RemoteServicesConfig? = null
    private val isServiceAvailable get() = serviceConfig!=null


    private val retrofitInstance by lazy{
        Retrofit.Builder()
            .baseUrl(serviceConfig!!.realTimeService.uri)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    private val serviceApi: RealTimeServiceApi by lazy{
        retrofitInstance.create(RealTimeServiceApi::class.java)
    }

    // initializer block
    init {
        loadServiceProperties()
    }

    private fun loadServiceProperties() {

        try{
            val serviceInfoContent = javaClass.getResource("/res/raw/quoders_services.json")?.readText()
            val moshi = Moshi.Builder().build()
            val adapter: JsonAdapter<RemoteServicesConfig> = moshi.adapter(RemoteServicesConfig::class.java)
            serviceConfig = adapter.fromJson(serviceInfoContent)
        }
        catch (ex: Exception)
        {
            Timber.e("can not load realtime service configuration: $ex")
        }
    }


    override suspend fun getStopNextTransports(stop: Stop): List<StopNextTransports> {
        if (!isServiceAvailable)
            throw IllegalStateException("The service configuration has not been loaded")

        try {
            val uri = serviceConfig!!.realTimeService.getTransportQueryUri(stop.code)
            val arrivals = serviceApi.getRealTime(uri.toString(), "someCookie")
            return arrivalsToStopNextTransport(arrivals)
        }
        catch (ex: Exception)
        {
            Timber.e("can not get next transport data due to exception: $ex")
        }
        return emptyList()
    }

    private fun arrivalsToStopNextTransport(arrivals: Arrivals): List<StopNextTransports> {
        // TODO NOT IMPLEMENTED
        return listOf()
    }
}