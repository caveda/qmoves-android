package com.quoders.apps.qmoves.stopDetail.next

import androidx.lifecycle.*
import com.quoders.apps.qmoves.Event
import com.quoders.apps.qmoves.R
import com.quoders.apps.qmoves.data.*
import com.quoders.apps.qmoves.data.source.TransportRepository
import com.quoders.apps.qmoves.realTime.RealTimeService
import com.quoders.apps.qmoves.realTime.model.TransportRealTimeArrival
import com.quoders.apps.qmoves.tools.TimeUtils
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 *  ViewModel of StopNext details fragment
 */
class StopNextViewModel (private val stop: Stop, private val line: Line,
                         private val realTimeService: RealTimeService,
                         private val repository: TransportRepository) : ViewModel() {

    private lateinit var connectionLines: List<Line>

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
        loadStopArrivals()
    }

    private fun loadStopArrivals() {
        viewModelScope.launch {
            buildDefaultArrivalList()
            getStopRealTime()
        }
    }

    private suspend fun buildDefaultArrivalList()
    {
        val result = mutableListOf<StopNextLineTransport>()
        connectionLines = getConnectionsLines(stop)
        connectionLines.forEach { connection ->
            result.add(
                StopNextLineTransport(
                    lineId = connection.agencyId,
                    stopId = stop.code,
                    realtimeQueryInProgress = true,
                    realtimeLoadingStatus = DataLoadingStatus.LOADING,
                    minutesToArrival = "",
                    lineName = connection.name
                )
            )
        }
        _nextTransports.value = result
    }

    private suspend fun getConnectionsLines(stop: Stop): List<Line> {
        val result = mutableListOf<Line>()
        result.add (line)
        val connectionsLinesResult = repository.getStopConnectionsLines(stop)
        if (connectionsLinesResult is Result.Success) {
            result.addAll(connectionsLinesResult.data)
        }
        return result
    }

    private suspend fun getStopRealTime() {
        _dataLoading.value = DataLoadingStatus.LOADING
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

    private fun mapToStopNextLineTransport(arrivals: List<TransportRealTimeArrival>): List<StopNextLineTransport>? {
        val result = mutableListOf<StopNextLineTransport>()
        connectionLines.forEach {connection ->
            val nextArrival = arrivals.find{a -> a.lineId==connection.agencyId}
            nextArrival?.let { arrival ->
                result.add(
                    StopNextLineTransport(
                        lineId = connection.agencyId,
                        stopId = arrival.stopId,
                        realtimeQueryInProgress = false,
                        realtimeLoadingStatus = DataLoadingStatus.DONE,
                        lineName = connection.name,
                        minutesToArrival = transportTimeToMinutes(arrival.arrivalTimeEpochSeconds)
                    )
                )
            }
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