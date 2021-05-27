package com.quoders.apps.qmoves.stopDetail.next

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.quoders.apps.qmoves.data.Line
import com.quoders.apps.qmoves.data.Stop
import com.quoders.apps.qmoves.data.source.TransportRepository
import com.quoders.apps.qmoves.services.realTime.RealTimeService

/**
 *  Factory for creating parametrized StopNextViewModel instances
 */
class StopNextViewModelFactory(private val stop: Stop, private val line: Line,
                               private val repository: TransportRepository,
                               private val realTimeService: RealTimeService) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StopNextViewModel::class.java)) {
            return StopNextViewModel(stop,line, repository, realTimeService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}