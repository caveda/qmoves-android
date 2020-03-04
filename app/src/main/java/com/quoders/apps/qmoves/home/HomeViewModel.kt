package com.quoders.apps.qmoves.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.quoders.apps.qmoves.Event
import com.quoders.apps.qmoves.data.Transport

/**
 * Viewmodel of the Home page
 */
class HomeViewModel: ViewModel() {

    // Events
    private val _eventNavigateLines = MutableLiveData<Event<Transport>>()
    val eventNavigateLines : LiveData<Event<Transport>> = _eventNavigateLines

    fun navigateToLines (transport: Transport) {
        _eventNavigateLines.value = Event(transport)
    }
}