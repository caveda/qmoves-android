package com.quoders.apps.qmoves.stopDetail.next

import androidx.lifecycle.*
import com.quoders.apps.qmoves.Event
import com.quoders.apps.qmoves.R
import com.quoders.apps.qmoves.data.DataLoadingStatus
import com.quoders.apps.qmoves.data.Favorite
import com.quoders.apps.qmoves.data.Result
import com.quoders.apps.qmoves.data.Stop
import com.quoders.apps.qmoves.data.source.TransportRepository
import com.quoders.apps.qmoves.realTime.RealTimeService
import kotlinx.coroutines.launch

/**
 *  ViewModel of StopNext details fragment
 */
class StopNextViewModel (private val stop: Stop, private val realTimeService: RealTimeService) : ViewModel() {

    init {
        getStopRealTime()
    }

    private fun getStopRealTime() {
        viewModelScope.launch {
            val arrivals = realTimeService.getStopNextTransports(stop)
        }
    }
}