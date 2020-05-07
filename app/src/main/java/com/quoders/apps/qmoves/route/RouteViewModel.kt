package com.quoders.apps.qmoves.route

import androidx.lifecycle.*
import com.google.android.gms.maps.model.LatLng
import com.quoders.apps.qmoves.Event
import com.quoders.apps.qmoves.R
import com.quoders.apps.qmoves.data.DataLoadingStatus
import com.quoders.apps.qmoves.data.Line
import com.quoders.apps.qmoves.data.Result
import com.quoders.apps.qmoves.data.Stop
import com.quoders.apps.qmoves.data.source.TransportRepository
import kotlinx.coroutines.launch

/**
 *  ViewModel of Stops page
 */
class RouteViewModel (private val line: Line,
                      private val repository: TransportRepository
) : ViewModel() {

    // Route to be painted
    private val _route = MutableLiveData<List<LatLng>>()
    val route : LiveData<List<LatLng>> = _route

    // Stops
    private val _stops = MutableLiveData<List<Stop>>()
    val stops : LiveData<List<Stop>> = _stops

    // Autocalculated property that flags when the empty list placeholder need to be visible.
    val empty: LiveData<Boolean> = Transformations.map(_route) {
        it.isEmpty()
    }

    // State of the loading operation
    private val _dataLoading = MutableLiveData<DataLoadingStatus>()
    val dataLoading: LiveData<DataLoadingStatus> = _dataLoading

    // Message to display to user
    private val _snackbarText = MutableLiveData<Event<Int>>()
    val snackbarText: LiveData<Event<Int>> = _snackbarText

    init {
        loadMapData()
    }

    private fun loadMapData() {
        viewModelScope.launch {
            _dataLoading.value = DataLoadingStatus.LOADING
            if (!loadLineRoute() || !loadLineStops()) {
                _dataLoading.value = DataLoadingStatus.ERROR
                showSnackbarMessage(R.string.error_loading_route)
            }
            else {
                _dataLoading.value = DataLoadingStatus.DONE
            }
        }
    }

    private suspend fun loadLineRoute() : Boolean {
        val route = repository.getRoute(line)
        var result = false
        if (route is Result.Success) {
            _route.value = route.data.map { l -> l.toLatLng() }
            result = true
        }
        return result
    }

    private suspend fun loadLineStops() : Boolean {
        val stopsResult = repository.getLineStops(line)
        var result = false
        if (stopsResult is Result.Success) {
            _stops.value = stopsResult.data
            result = true
        }
        return result
    }

    private fun showSnackbarMessage(message: Int) {
        _snackbarText.value = Event(message)
    }
}