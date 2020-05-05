package com.quoders.apps.qmoves.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.quoders.apps.qmoves.Event
import com.quoders.apps.qmoves.R
import com.quoders.apps.qmoves.data.DataLoadingStatus
import com.quoders.apps.qmoves.data.Transport
import com.quoders.apps.qmoves.data.source.TransportRepository
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * Viewmodel of the Home page
 */
class HomeViewModel(private val transportRepository: TransportRepository): ViewModel() {

    // Events
    private val _eventNavigateLines = MutableLiveData<Event<Transport>>()
    val eventNavigateLines : LiveData<Event<Transport>> = _eventNavigateLines

    // State of the loading operation
    private val _dataLoading = MutableLiveData<DataLoadingStatus>()
    val dataLoading: LiveData<DataLoadingStatus> = _dataLoading

    // Transport data
    private val _transport = MutableLiveData<Transport>()
    val transport: LiveData<Transport> = _transport

    // Message to display to user
    private val _snackbarText = MutableLiveData<Event<Int>>()
    val snackbarText: LiveData<Event<Int>> = _snackbarText

    init {
        _transport.value = Transport(name = "Bus")
        updateTransitData()
    }

    fun navigateToLines (transport: Transport) {
        _eventNavigateLines.value = Event(transport)
    }

    private fun updateTransitData() {
        viewModelScope.launch {
            try {
                _dataLoading.value = DataLoadingStatus.LOADING
                transportRepository.getLines(Transport("bus"))
                _dataLoading.value = DataLoadingStatus.DONE
                showSnackbarMessage(R.string.update_successful)
            } catch (e: Exception) {
                Timber.e("updateTransitData: Exception catched: ${e.message}")
                _dataLoading.value = DataLoadingStatus.ERROR
                showSnackbarMessage(R.string.error_update_remote)
            }
        }
    }

    private fun showSnackbarMessage(message: Int) {
        _snackbarText.value = Event(message)
    }
}