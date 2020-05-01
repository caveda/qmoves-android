package com.quoders.apps.qmoves.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.quoders.apps.qmoves.data.Transport
import com.quoders.apps.qmoves.data.source.TransportRepository
import com.quoders.apps.qmoves.data.source.remote.FirebaseClientConfig
import com.quoders.apps.qmoves.getOrAwaitValue
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class HomeViewModelTest {

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var sut: HomeViewModel
    private lateinit var mockRepository: TransportRepository

    private val sutConfig = FirebaseClientConfig(
                funcUrl = "fakeUrl",
                funcHeaderValue = "fakeHeader",
                storageMetadataPath = "fakeMetadataPath",
                storageDataPath = "fakeDataPath")

    @Before
    fun setUp() {
        mockRepository = mockk()
        sut = HomeViewModel(mockRepository)
    }

    @Test
    fun navigateToLines_invoked_navigateEventSet() {
        // Given
        val transport = Transport(name = "SubwayXYZ")

        // When
        sut.navigateToLines(transport)

        // Then
        val value = sut.eventNavigateLines.getOrAwaitValue()
        assertEquals(value.getContentIfNotHandled(),transport)
    }
}