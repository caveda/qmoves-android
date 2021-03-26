package com.quoders.apps.qmoves.stopDetail.next

import androidx.lifecycle.*
import com.quoders.apps.qmoves.Event
import com.quoders.apps.qmoves.R
import com.quoders.apps.qmoves.data.*
import com.quoders.apps.qmoves.realTime.RealTimeService
import com.quoders.apps.qmoves.realTime.model.StopNextTransport
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 *  ViewModel of StopNext details fragment
 */
class StopNextViewModel (private val stop: Stop, private val realTimeService: RealTimeService) : ViewModel() {

    // State of the loading operation
    private val _dataLoading = MutableLiveData<DataLoadingStatus>()
    val dataLoading: LiveData<DataLoadingStatus> = _dataLoading

    // Transport data
    private val _nextTransports = MutableLiveData<List<StopNextTransport>>()
    val nextTransports: LiveData<List<StopNextTransport>> = _nextTransports

    // Autocalculated property that flags when the empty list placeholder need to be visible.
    val empty: LiveData<Boolean> = Transformations.map(_nextTransports) {
        it.isEmpty()
    }

    // Message to display to user
    private val _snackbarText = MutableLiveData<Event<Int>>()
    val snackbarText: LiveData<Event<Int>> = _snackbarText

    init {
        _nextTransports.value = listOf()
        getStopRealTime()
    }

    private fun getStopRealTime() {
        _dataLoading.value = DataLoadingStatus.LOADING
        viewModelScope.launch {
            try {
                val result = realTimeService.getStopNextTransports(stop)
                if (result is Result.Success) {
                    _nextTransports.value = result.data
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

    private fun showSnackbarMessage(message: Int) {
        _snackbarText.value = Event(message)
    }
}