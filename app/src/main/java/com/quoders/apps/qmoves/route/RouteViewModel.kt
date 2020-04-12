package com.quoders.apps.qmoves.route

import androidx.lifecycle.*
import com.quoders.apps.qmoves.Event
import com.quoders.apps.qmoves.R
import com.quoders.apps.qmoves.data.*
import kotlinx.coroutines.launch

/**
 *  ViewModel of Route page
 */
class RouteViewModel (val transport: Transport, val route: List<Location>) : ViewModel() {

    // list of location of the route to show
    private val _points = MutableLiveData<List<Location>>()
    val points : LiveData<List<Location>> = _points

    init {
        loadRoute()
    }

    private fun loadRoute() {
        _points.value = route
    }
}