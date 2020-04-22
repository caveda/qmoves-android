package com.quoders.apps.qmoves.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.quoders.apps.qmoves.data.source.remote.FirebaseClientConfig

/**
 *  Factory for creating parametrized HomeViewModel instances
 */
class HomeViewModelFactory(private val config: FirebaseClientConfig) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(config) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}