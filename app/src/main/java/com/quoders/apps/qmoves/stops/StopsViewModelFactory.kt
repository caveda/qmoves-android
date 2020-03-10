package com.quoders.apps.qmoves.stops

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.quoders.apps.qmoves.data.Line
import com.quoders.apps.qmoves.data.Transport
import com.quoders.apps.qmoves.data.source.TransportRepository
import com.quoders.apps.qmoves.lines.LinesViewModel

/**
 *  Factory for creating parametrized StopsViewModel instances
 */
class StopsViewModelFactory(private val transport: Transport, private val line: Line) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StopsViewModel::class.java)) {
            return StopsViewModel(transport, line) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}