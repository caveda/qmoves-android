package com.quoders.apps.qmoves.favorites

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.quoders.apps.qmoves.CoroutineTestRule
import com.quoders.apps.qmoves.R
import com.quoders.apps.qmoves.data.DataLoadingStatus
import com.quoders.apps.qmoves.data.Line
import com.quoders.apps.qmoves.data.Result
import com.quoders.apps.qmoves.data.Transport
import com.quoders.apps.qmoves.data.source.TransportRepository
import com.quoders.apps.qmoves.getOrAwaitValue
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test

/**
 * Unit tests for FavoritesViewModel class
 */
class FavoritesViewModelTest {

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    val coroutineTestRule = CoroutineTestRule()


    private lateinit var mockRepository: TransportRepository
    private lateinit var mockTransport: Transport
    private lateinit var sut: FavoritesViewModel

    @Before
    fun setUp() {
        // Mocked objects
        mockTransport = mockk()
        mockRepository = mockk()

        // Basic training
        coEvery { mockRepository.getAllFavorites()} returns Result.Success(listOf())
    }

    @Test
    @Ignore
    fun navigateToStops_invoked_navigateEventSet() = runBlockingTest {
        // Given
        sut = FavoritesViewModel(mockRepository)
        val favorite = TestDataFavorites.validFavoriteList[0]

        // When
        sut.navigateToFavoriteDetail(favorite)
        val value = sut.eventNavigateToStopDetail.getOrAwaitValue()

        // Then
        assertThat(value.getContentIfNotHandled()).isEqualTo(favorite.stop)
    }

    @Test
    fun getFavorites_noFavoritesInRepository_favoritesIsEmpty() = runBlockingTest {
        // When
        sut = FavoritesViewModel(mockRepository)
        val value = sut.favorites.getOrAwaitValue()

        // Then
        assertThat(value).isEmpty()
    }

    @Test
    fun getFavorites_notEmptyFavoriteListInRepository_allFavoritesReturned() = runBlockingTest {
        // Given
        coEvery { mockRepository.getAllFavorites() } returns Result.Success(TestDataFavorites.validFavoriteList)

        // When
        sut = FavoritesViewModel(mockRepository)

        // Then
        val value = sut.favorites.getOrAwaitValue()
        assertThat(value).isEqualTo(TestDataFavorites.validFavoriteList)
    }

    @Test
    fun getFavorites_errorLoadingFavorites_emptyFavoritesList() = runBlockingTest {
        // Given
        coEvery { mockRepository.getAllFavorites() } returns Result.Error(Exception("loading error"))

        // When
        sut = FavoritesViewModel(mockRepository)
        val value = sut.favorites.getOrAwaitValue()

        // Then
        assertThat(value).isEmpty()
    }

    @Test
    fun getEmpty_noFavoritesInRepository_emptyIsTrue() = runBlockingTest {
        // When
        sut = FavoritesViewModel(mockRepository)
        val value = sut.empty.getOrAwaitValue()

        // Then
        assertThat(value).isTrue()
    }

    @Test
    fun getSnackbarText_errorLoadingFavorites_snackBarTextSetWithError() = runBlockingTest {
        // Given
        coEvery { mockRepository.getAllFavorites() } returns Result.Error(Exception("loading error"))

        // When
        sut = FavoritesViewModel(mockRepository)
        val value = sut.snackbarText.getOrAwaitValue()

        // Then
        assertThat(value.getContentIfNotHandled()).isEqualTo(R.string.error_loading_favorites)
    }

    @Test
    fun getDataLoading_errorLoadingFavorites_dataLoadingIsError() {
        // Given
        coEvery { mockRepository.getAllFavorites() } returns Result.Error(Exception("loading error"))

        // When
        sut = FavoritesViewModel(mockRepository)
        val value = sut.dataLoading.getOrAwaitValue()

        // Then
        assertThat(value).isEqualTo(DataLoadingStatus.ERROR)
    }

    @Test
    fun getDataLoading_linesLoaded_dataLoadingIsDone() {
        // Given
        coEvery { mockRepository.getAllFavorites() } returns Result.Success(TestDataFavorites.validFavoriteList)

        // When
        sut = FavoritesViewModel(mockRepository)

        // Then
        val value = sut.dataLoading.getOrAwaitValue()
        assertThat(value).isEqualTo(DataLoadingStatus.DONE)
    }
}