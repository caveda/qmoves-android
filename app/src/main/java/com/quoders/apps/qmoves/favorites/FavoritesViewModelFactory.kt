package com.quoders.apps.qmoves.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.quoders.apps.qmoves.data.Line
import com.quoders.apps.qmoves.data.source.TransportRepository
import com.quoders.apps.qmoves.stops.StopsViewModel

/**
 *  Factory for creating parametrized StopsViewModel instances
 */
class FavoritesViewModelFactory(private val repository: TransportRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoritesViewModel::class.java)) {
            return FavoritesViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}