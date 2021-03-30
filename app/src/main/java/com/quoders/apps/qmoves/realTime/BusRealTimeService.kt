package com.quoders.apps.qmoves.realTime

import android.util.Base64
import com.quoders.apps.qmoves.data.Stop
import com.quoders.apps.qmoves.realTime.api.RealTimeServiceApi
import com.quoders.apps.qmoves.realTime.model.*
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import timber.log.Timber
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec
import com.quoders.apps.qmoves.data.*
import com.quoders.apps.qmoves.tools.TimeUtils

/**
 * Real time service client for Bus
 */
class BusRealTimeService: RealTimeService {

    private var serviceConfig: RemoteServicesConfig? = null
    private val isServiceAvailable get() = serviceConfig!=null

    private val retrofitInstance by lazy{
        Retrofit.Builder()
            .baseUrl(serviceConfig!!.realTimeService.serviceBaseUri.toString())
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
            val adapter: JsonAdapter<RemoteServicesConfig> = moshi.adapter(
                RemoteServicesConfig::class.java)
            serviceConfig = adapter.fromJson(serviceInfoContent)
        }
        catch (ex: Exception)
        {
            Timber.e("can not load realtime service configuration: $ex")
        }
    }


    override suspend fun getStopNextTransports(stop: Stop): Result<List<StopNextTransport>> {
        if (!isServiceAvailable)
            throw IllegalStateException("The service configuration has not been loaded")

        try {
            val path = serviceConfig!!.realTimeService.getStopQueryPath(stop.code)
            val arrivals = serviceApi.getRealTime(path.toString(), buildMessage(serviceConfig!!.realTimeService, stop))
            return Result.Success(arrivalsToStopNextTransport(stop,arrivals))
        }
        catch (ex: Exception)
        {
            Timber.e("can not get next transport data due to exception: $ex")
            return Result.Error(ex)
        }
    }

    private fun arrivalsToStopNextTransport(stop: Stop, stopRealTimeInfo: Arrivals): List<StopNextTransport> {
        val nextTransports = mutableListOf<StopNextTransport>()
        stopRealTimeInfo.arrivals.forEach { nextTransports.add(StopNextTransport(
            lineId = it.line,
            stopId = stop.code,
            arrivalTime = it.time,
            minutesToArrival = transportTimeToMinutes(it.time)
        ))}
        return nextTransports
    }

    fun buildMessage(config: RealTimeServiceConfig, stop: Stop): String {
        val ts = (System.currentTimeMillis() / 1000L).toString();
        val final = String.format(config.content,config.getStopQueryPath(stop.code),ts).toByteArray()
        val instance = Mac.getInstance(config.algorithm)
        instance.init(SecretKeySpec(config.token.toByteArray(), config.algorithm))
        return String.format(config.text,Base64.encodeToString(instance.doFinal(final),Base64.NO_WRAP),ts)
    }

    fun transportTimeToMinutes (arrivalTime: String): String {
        val minutes = TimeUtils.MinutesTo(arrivalTime)
        return if (minutes<0) "-" else minutes.toString()
    }
}