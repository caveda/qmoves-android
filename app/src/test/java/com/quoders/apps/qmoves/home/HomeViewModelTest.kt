package com.quoders.apps.qmoves.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.quoders.apps.qmoves.data.Result
import com.quoders.apps.qmoves.data.source.TransportRepository
import com.quoders.apps.qmoves.getOrAwaitValue
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import com.google.common.truth.Truth.assertThat
import com.quoders.apps.qmoves.CoroutineTestRule
import com.quoders.apps.qmoves.data.DataLoadingStatus
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class HomeViewModelTest {

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    private lateinit var sut: HomeViewModel
    private lateinit var mockRepository: TransportRepository

    @Before
    fun setUp() {
        mockRepository = mockk()

        // Default behaviour
        coEvery { mockRepository.getTransports()} returns Result.Success(listOf())
    }

    @Test
    fun navigateToLines_invoked_navigateEventSet() {
        // Given
        val transport = TestDataTransports.defaultTestTransport
        sut = HomeViewModel(mockRepository)

        // When
        sut.navigateToLines(transport)

        // Then
        val value = sut.eventNavigateLines.getOrAwaitValue()
        assertEquals(value.getContentIfNotHandled(),transport)
    }

    @Test
    fun getTransports_noTransportsInRepository_transportsIsEmpty() = runBlockingTest {
        // When
        sut = HomeViewModel(mockRepository)
        val value = sut.transports.getOrAwaitValue()

        // Then
        assertThat(value).isEmpty()
    }

    @Test
    fun getTransports_validTransportsListInRepository_listOfTransportsReturned() = runBlockingTest {
        // Given
        coEvery { mockRepository.getTransports() } returns Result.Success(TestDataTransports.validTransportList)

        // When
        sut = HomeViewModel(mockRepository)
        val value = sut.transports.getOrAwaitValue()

        // Then
        val expectedTransports = TestDataTransports.validTransportList
        assertThat(value).isEqualTo(expectedTransports)
    }

    @Test
    fun getTransports_errorLoadingTransports_emptyTransportList() = runBlockingTest {
        // Given
        coEvery { mockRepository.getTransports() } returns Result.Error(Exception("loading error"))

        // When
        sut = HomeViewModel(mockRepository)
        val value = sut.transports.getOrAwaitValue()

        // Then
        assertThat(value).isEmpty()
    }

    @Test
    fun getTransports_errorLoadingTransports_statusLoadingIsError() = runBlockingTest {
        // Given
        coEvery { mockRepository.getTransports() } returns Result.Error(Exception("loading error"))

        // When
        sut = HomeViewModel(mockRepository)
        val value = sut.dataLoading.getOrAwaitValue()

        // Then
        assertThat(value).isEqualTo(DataLoadingStatus.ERROR)
    }

    @Test
    fun getDataLoading_linesLoaded_dataLoadingIsDone() {
        // Given
        coEvery { mockRepository.getTransports() } returns Result.Success(TestDataTransports.validTransportList)

        // When
        sut =HomeViewModel(mockRepository)
        val value = sut.dataLoading.getOrAwaitValue()

        // Then
        assertThat(value).isEqualTo(DataLoadingStatus.DONE)
    }
}