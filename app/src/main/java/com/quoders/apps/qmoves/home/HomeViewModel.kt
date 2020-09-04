package com.quoders.apps.qmoves.home

import androidx.lifecycle.*
import com.quoders.apps.qmoves.Event
import com.quoders.apps.qmoves.R
import com.quoders.apps.qmoves.data.DataLoadingStatus
import com.quoders.apps.qmoves.data.Result
import com.quoders.apps.qmoves.data.Transport
import com.quoders.apps.qmoves.data.source.TransportRepository
import kotlinx.coroutines.launch
import timber.log.Timber
import kotlin.reflect.jvm.internal.impl.types.checker.TypeCheckerContext

/**
 * Viewmodel of the Home page
 */
class HomeViewModel(private val repository: TransportRepository): ViewModel() {

    // Events
    private val _eventNavigateLines = MutableLiveData<Event<Transport>>()
    val eventNavigateLines : LiveData<Event<Transport>> = _eventNavigateLines

    private val _eventNavigateFavorites = MutableLiveData<Event<Boolean>>()
    val eventNavigateFavorites : LiveData<Event<Boolean>> = _eventNavigateFavorites

    // State of the loading operation
    private val _dataLoading = MutableLiveData<DataLoadingStatus>()
    val dataLoading: LiveData<DataLoadingStatus> = _dataLoading

    // Transport data
    private val _transports = MutableLiveData<List<Transport>>()
    val transports: LiveData<List<Transport>> = _transports

    // Autocalculated property that flags when the empty list placeholder need to be visible.
    val empty: LiveData<Boolean> = Transformations.map(_transports) {
        it.isEmpty()
    }

    // Message to display to user
    private val _snackbarText = MutableLiveData<Event<Int>>()
    val snackbarText: LiveData<Event<Int>> = _snackbarText

    init {
        _transports.value = listOf()
        loadTransports()
    }

    fun navigateToLines (transport: Transport) {
        _eventNavigateLines.value = Event(transport)
    }

    fun navigateToFavorites () {
        _eventNavigateFavorites.value = Event(true)
    }

    private fun loadTransports() {
        _dataLoading.value = DataLoadingStatus.LOADING
        viewModelScope.launch {
            try {
                val result = repository.getTransports()
                if (result is Result.Success) {
                    _transports.value = result.data
                    _dataLoading.value = DataLoadingStatus.DONE
                }
                else {
                    _dataLoading.value = DataLoadingStatus.ERROR
                    showSnackbarMessage(R.string.error_loading_lines)
                }
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