package com.quoders.apps.qmoves.stopDetail.next

import androidx.lifecycle.*
import com.quoders.apps.qmoves.Event
import com.quoders.apps.qmoves.R
import com.quoders.apps.qmoves.data.*
import com.quoders.apps.qmoves.realTime.RealTimeService
import com.quoders.apps.qmoves.realTime.model.TransportRealTimeArrival
import com.quoders.apps.qmoves.tools.TimeUtils
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 *  ViewModel of StopNext details fragment
 */
class StopNextViewModel (private val stop: Stop, private val line: Line, private val realTimeService: RealTimeService) : ViewModel() {

    // State of the loading operation
    private val _dataLoading = MutableLiveData<DataLoadingStatus>()
    val dataLoading: LiveData<DataLoadingStatus> = _dataLoading

    // Transport data
    private val _nextTransports = MutableLiveData<List<StopNextLineTransport>>()
    val nextTransports: LiveData<List<StopNextLineTransport>> = _nextTransports

    // Autocalculated property that flags when the empty list placeholder need to be visible.
    val empty: LiveData<Boolean> = Transformations.map(_nextTransports) {
        it.isEmpty()
    }

    // Message to display to user
    private val _snackbarText = MutableLiveData<Event<Int>>()
    val snackbarText: LiveData<Event<Int>> = _snackbarText

    init {
        _nextTransports.value = buildDefaultNextTransportsList()
        getStopRealTime()
    }

    private fun buildDefaultNextTransportsList(): List<StopNextLineTransport> {
        val result = mutableListOf<StopNextLineTransport>()
        getStopConnectionsList(stop.connections).forEach { connection ->
            result.add(
                StopNextLineTransport(
                    lineId = connection,
                    stopId = stop.code,
                    realtimeQueryInProgress = true,
                    realtimeLoadingStatus = DataLoadingStatus.LOADING,
                    minutesToArrival = "",
                    lineName = line.name
            ))
        }
        return result
    }

    private fun getStopConnectionsList(connections: String?): List<String> {
        val result = mutableListOf<String>()
        result.add (line.agencyId)
        connections?.let {
            val lineIds = it.split(" ")
            lineIds.forEach { c ->
                val id = c.substring(1,3)
                if (!result.contains(id))
                    result.add(id)
            }
        }
        return result
    }

    private fun getStopRealTime() {
        _dataLoading.value = DataLoadingStatus.LOADING
        viewModelScope.launch {
            try {
                val result = realTimeService.getStopNextTransports(stop)
                if (result is Result.Success) {
                    _nextTransports.value = mapToStopNextLineTransport(result.data)
                    _dataLoading.value = DataLoadingStatus.DONE

                } else {
                    _dataLoading.value = DataLoadingStatus.ERROR
                    showSnackbarMessage(R.string.error_loading_lines)
                }
            }
            catch (e:Exception) {
                Timber.e("getNextTransports: Exception catched: ${e.message}")
                _dataLoading.value = DataLoadingStatus.ERROR
                showSnackbarMessage(R.string.error_update_remote)
            }
        }
    }

    private fun mapToStopNextLineTransport(arrivals: List<TransportRealTimeArrival>): List<StopNextLineTransport>? {
        val result = mutableListOf<StopNextLineTransport>()
        arrivals.forEach {
            result.add(
                StopNextLineTransport(
                    lineId = it.lineId,
                    stopId = it.stopId,
                    realtimeQueryInProgress = false,
                    realtimeLoadingStatus = DataLoadingStatus.DONE,
                    lineName = line.name,
                    minutesToArrival = transportTimeToMinutes(it.arrivalTimeEpochSeconds)
                )
            )
        }
        return result
    }

    private fun showSnackbarMessage(message: Int) {
        _snackbarText.value = Event(message)
    }

    fun transportTimeToMinutes (epochSeconds: Long): String {
        val minutes = TimeUtils.MinutesTo(epochSeconds)
        return if (minutes<0) "-" else minutes.toString()
    }
}