package com.quoders.apps.qmoves.stops

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.quoders.apps.qmoves.data.Line
import com.quoders.apps.qmoves.data.source.TransportRepository

/**
 *  Factory for creating parametrized StopsViewModel instances
 */
class StopsViewModelFactory(private val line: Line,
                            private val repository: TransportRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StopsViewModel::class.java)) {
            return StopsViewModel(line, repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}