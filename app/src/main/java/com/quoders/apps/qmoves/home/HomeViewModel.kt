package com.quoders.apps.qmoves.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.quoders.apps.qmoves.Event
import com.quoders.apps.qmoves.data.Transport
import com.quoders.apps.qmoves.data.source.remote.FirebaseClient
import com.quoders.apps.qmoves.data.source.remote.FirebaseClientConfig
import kotlinx.coroutines.launch
import java.util.ArrayList

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

    fun updateTransitData(config: FirebaseClientConfig) {
        viewModelScope.launch {
            try {
                val client = FirebaseClient(config)
                client.update()
            } catch (e: Exception) {
                val error = e.message
            }
        }
    }
}