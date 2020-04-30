package com.quoders.apps.qmoves.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.quoders.apps.qmoves.Event
import com.quoders.apps.qmoves.R
import com.quoders.apps.qmoves.data.DataLoadingStatus
import com.quoders.apps.qmoves.data.Line
import com.quoders.apps.qmoves.data.Result
import com.quoders.apps.qmoves.data.TransportAgency
import com.quoders.apps.qmoves.data.source.FakeTransportRepository
import com.quoders.apps.qmoves.data.source.remote.FirebaseClient
import com.quoders.apps.qmoves.data.source.remote.FirebaseClientConfig
import com.quoders.apps.qmoves.data.source.remote.RemoteLine
import kotlinx.coroutines.launch

/**
 * Viewmodel of the Home page
 */
class HomeViewModel(private val config: FirebaseClientConfig): ViewModel() {

    // Events
    private val _eventNavigateLines = MutableLiveData<Event<TransportAgency>>()
    val eventNavigateLines : LiveData<Event<TransportAgency>> = _eventNavigateLines

    // State of the loading operation
    private val _dataLoading = MutableLiveData<DataLoadingStatus>()
    val dataLoading: LiveData<DataLoadingStatus> = _dataLoading

    // Transport data
    private val _transport = MutableLiveData<TransportAgency>()
    val transport: LiveData<TransportAgency> = _transport

    // Message to display to user
    private val _snackbarText = MutableLiveData<Event<Int>>()
    val snackbarText: LiveData<Event<Int>> = _snackbarText

    init {
        _transport.value = TransportAgency(name = "Bus")
        updateTransitData()
    }

    fun navigateToLines (transport: TransportAgency) {
        _eventNavigateLines.value = Event(transport)
    }

    private fun updateTransitData() {
        viewModelScope.launch {
            try {
                _dataLoading.value = DataLoadingStatus.LOADING
                val client = FirebaseClient(config)
                val onCompleted : (Result<List<RemoteLine>>) -> Any = { r ->
                    //handleUpdateResult(r)
                }
                client.update(onCompleted)
            } catch (e: Exception) {
                val error = e.message
                _dataLoading.value = DataLoadingStatus.ERROR
                showSnackbarMessage(R.string.error_update_remote)
            }
        }
    }

    private fun showSnackbarMessage(message: Int) {
        _snackbarText.value = Event(message)
    }
/*
    private fun handleUpdateResult(r: Result<List<RemoteLine>>) {
        if (r is Result.Success) {
            val newRepo = FakeTransportRepository()
            newRepo.setLines(r.data)
            _transport.value!!.repository = newRepo
            _dataLoading.value = DataLoadingStatus.DONE
            showSnackbarMessage(R.string.update_successful)
        } else {
            _dataLoading.value = DataLoadingStatus.ERROR
            showSnackbarMessage(R.string.error_update_remote)
        }
    }*/
}