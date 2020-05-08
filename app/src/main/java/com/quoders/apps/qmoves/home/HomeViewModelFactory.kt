package com.quoders.apps.qmoves.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.quoders.apps.qmoves.data.source.TransportRepository
import com.quoders.apps.qmoves.data.source.remote.FirebaseClientConfig

/**
 *  Factory for creating parametrized HomeViewModel instances
 */
class HomeViewModelFactory(val transportRepository: TransportRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(transportRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}