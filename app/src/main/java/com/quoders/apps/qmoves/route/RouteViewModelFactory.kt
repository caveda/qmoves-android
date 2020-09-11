package com.quoders.apps.qmoves.route

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.quoders.apps.qmoves.data.Line
import com.quoders.apps.qmoves.data.Transport
import com.quoders.apps.qmoves.data.source.TransportRepository

/**
 *  Factory for creating parametrized RouteViewModel instances
 */
class RouteViewModelFactory(private val transport: Transport, private val line: Line,
                            private val repository: TransportRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RouteViewModel::class.java)) {
            return RouteViewModel(transport, line, repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}