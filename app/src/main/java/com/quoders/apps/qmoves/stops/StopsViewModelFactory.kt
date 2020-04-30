package com.quoders.apps.qmoves.stops

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.quoders.apps.qmoves.data.Line
import com.quoders.apps.qmoves.data.TransportAgency

/**
 *  Factory for creating parametrized StopsViewModel instances
 */
class StopsViewModelFactory(private val transport: TransportAgency, private val line: Line) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StopsViewModel::class.java)) {
            return StopsViewModel(transport, line) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}