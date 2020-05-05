package com.quoders.apps.qmoves.stops

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.quoders.apps.qmoves.Event
import com.quoders.apps.qmoves.R
import com.quoders.apps.qmoves.data.DataLoadingStatus
import com.quoders.apps.qmoves.data.Line
import com.quoders.apps.qmoves.data.Stop
import com.quoders.apps.qmoves.data.Transport
import com.quoders.apps.qmoves.data.source.TransportRepository


/**
 *  ViewModel of Stops page
 */
class StopsViewModel (private val transport: Transport, private val line: Line,
                      private val repository: TransportRepository
) : ViewModel() {

    // Stop items to be listed in the page
    private val _stops = MutableLiveData<List<Stop>>()
    val stops : LiveData<List<Stop>> = _stops

    // Autocalculated property that flags when the empty list placeholder need to be visible.
    val empty: LiveData<Boolean> = Transformations.map(_stops) {
        it.isEmpty()
    }

    // State of the loading operation
    private val _dataLoading = MutableLiveData<DataLoadingStatus>()
    val dataLoading: LiveData<DataLoadingStatus> = _dataLoading

    // Message to display to user
    private val _snackbarText = MutableLiveData<Event<Int>>()
    val snackbarText: LiveData<Event<Int>> = _snackbarText

    // Navigation
    private val _eventNavigateToStopDetail = MutableLiveData<Event<Stop>>()
    val eventNavigateToStopDetail : LiveData<Event<Stop>> = _eventNavigateToStopDetail

    init {
        loadStops()
    }

    private fun loadStops() {
        _dataLoading.value = DataLoadingStatus.LOADING
        if (line.stops.isNotEmpty()) {
            _stops.value = line.stops
            _dataLoading.value = DataLoadingStatus.DONE
        }
        else {
            _dataLoading.value = DataLoadingStatus.ERROR
            showSnackbarMessage(R.string.error_loading_stops)
        }
    }

    private fun showSnackbarMessage(message: Int) {
        _snackbarText.value = Event(message)
    }

    fun navigateToStopDetail(stop: Stop) {
        _eventNavigateToStopDetail.value = Event(stop)
    }
}