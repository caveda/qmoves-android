package com.quoders.apps.qmoves.lines

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.quoders.apps.qmoves.data.TransportAgency
import com.quoders.apps.qmoves.data.source.TransportRepository

/**
 *  Factory for creating parametrized LinesViewModel instances
 */
class LinesViewModelFactory(private val transport: TransportAgency) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LinesViewModel::class.java)) {
            return LinesViewModel(transport) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}