package com.quoders.apps.qmoves.stops

import androidx.lifecycle.*
import com.quoders.apps.qmoves.Event
import com.quoders.apps.qmoves.R
import com.quoders.apps.qmoves.data.*
import kotlinx.coroutines.launch

enum class DataLoadingStatus {DONE, LOADING, ERROR}

/**
 *  ViewModel of Stops page
 */
class StopsViewModel (val transport: Transport, val line: Line) : ViewModel() {

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