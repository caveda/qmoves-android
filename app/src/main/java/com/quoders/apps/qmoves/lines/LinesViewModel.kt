package com.quoders.apps.qmoves.lines

import androidx.lifecycle.*
import com.quoders.apps.qmoves.Event
import com.quoders.apps.qmoves.R
import com.quoders.apps.qmoves.data.Line
import com.quoders.apps.qmoves.data.Result
import com.quoders.apps.qmoves.data.Transport
import com.quoders.apps.qmoves.data.source.TransportRepository
import com.quoders.apps.qmoves.data.succeeded
import kotlinx.coroutines.launch

enum class DataLoadingStatus {DONE, LOADING, ERROR}

/**
 *  ViewModel of Lines page
 */
class LinesViewModel (val transport: Transport) : ViewModel() {

    private val transportRepository: TransportRepository = transport.repository

    private val _transportName = MutableLiveData<String>()
    val transportName : LiveData<String> = _transportName

    // Line items to be listed in the page
    private val _lines = MutableLiveData<List<Line>>()
    val lines : LiveData<List<Line>> = Transformations.map(_lines) { lines ->
        lines.filter{ line -> line.direction==Line.Direction.FORWARD}
    }

    // Autocalculated property that flags when the empty list placeholder need to be visible.
    val empty: LiveData<Boolean> = Transformations.map(_lines) {
        it.isEmpty()
    }

    // State of the loading operation
    private val _dataLoading = MutableLiveData<DataLoadingStatus>()
    val dataLoading: LiveData<DataLoadingStatus> = _dataLoading

    // Message to display to user
    private val _snackbarText = MutableLiveData<Event<Int>>()
    val snackbarText: LiveData<Event<Int>> = _snackbarText

    // Navigation
    private val _eventNavigateStops = MutableLiveData<Event<Line>>()
    val eventNavigateStops : LiveData<Event<Line>> = _eventNavigateStops

    init {
        _transportName.value = transport.name
        loadLines()
    }

    fun loadLines() {
        _dataLoading.value = DataLoadingStatus.LOADING
        viewModelScope.launch {
            val result = transportRepository.getLines()
            if (result is Result.Success) {
                _lines.value = result.data
                _dataLoading.value = DataLoadingStatus.DONE
            }
            else {
                _dataLoading.value = DataLoadingStatus.ERROR
                showSnackbarMessage(R.string.error_loading_lines)
            }
        }
    }

    private fun showSnackbarMessage(message: Int) {
        _snackbarText.value = Event(message)
    }

    fun navigateToStops(line: Line) {
        _eventNavigateStops.value = Event(line)
    }
}