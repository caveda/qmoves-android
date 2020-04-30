package com.quoders.apps.qmoves.lines

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.quoders.apps.qmoves.data.Transport

/**
 *  Factory for creating parametrized LinesViewModel instances
 */
class LinesViewModelFactory(private val transport: Transport) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LinesViewModel::class.java)) {
            return LinesViewModel(transport) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}