package com.quoders.apps.qmoves.favorites

import androidx.lifecycle.*
import com.quoders.apps.qmoves.Event
import com.quoders.apps.qmoves.R
import com.quoders.apps.qmoves.data.DataLoadingStatus
import com.quoders.apps.qmoves.data.Favorite
import com.quoders.apps.qmoves.data.Result
import com.quoders.apps.qmoves.data.Stop
import com.quoders.apps.qmoves.data.source.TransportRepository
import kotlinx.coroutines.launch


/**
 *  ViewModel of Favorites page
 */
class FavoritesViewModel (private val repository: TransportRepository) : ViewModel() {

    // Favorites to be listed in the page
    private val _favorites = MutableLiveData<List<Favorite>>()
    val favorites : LiveData<List<Favorite>> = _favorites

    // Autocalculated property that flags when the empty list placeholder need to be visible.
    val empty: LiveData<Boolean> = Transformations.map(_favorites) {
        it.isEmpty()
    }

    // State of the loading operation
    private val _dataLoading = MutableLiveData<DataLoadingStatus>()
    val dataLoading: LiveData<DataLoadingStatus> = _dataLoading

    // Message to display to user
    private val _snackbarText = MutableLiveData<Event<Int>>()
    val snackbarText: LiveData<Event<Int>> = _snackbarText

    // Navigation
    private val _eventNavigateToFavoriteDetail = MutableLiveData<Event<Stop>>()
    val eventNavigateToStopDetail : LiveData<Event<Stop>> = _eventNavigateToFavoriteDetail

    init {
        loadFavorites()
    }

    private fun loadFavorites() {
        viewModelScope.launch {
            _dataLoading.value = DataLoadingStatus.LOADING
            val result = repository.getAllFavorites()
            if (result is Result.Success) {
                _favorites.value = result.data
                _dataLoading.value = DataLoadingStatus.DONE
            }
            else {
                _dataLoading.value = DataLoadingStatus.ERROR
                showSnackbarMessage(R.string.error_loading_stops)
            }
        }
    }

    private fun showSnackbarMessage(message: Int) {
        _snackbarText.value = Event(message)
    }

    fun navigateToFavoriteDetail(stop: Stop) {
        _eventNavigateToFavoriteDetail.value = Event(stop)
    }
}