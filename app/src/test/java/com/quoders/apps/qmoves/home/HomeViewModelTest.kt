package com.quoders.apps.qmoves.home

import com.quoders.apps.qmoves.data.Transport
import com.quoders.apps.qmoves.getOrAwaitValue
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import androidx.arch.core.executor.testing.InstantTaskExecutorRule


class HomeViewModelTest {

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var sut: HomeViewModel

    @Before
    fun setUp() {
        sut = HomeViewModel()
    }

    @Test
    fun navigateToLines_invoked_navigateEventSet() {
        // Given
        val transport = Transport("SubwayXYZ")

        // When
        sut.navigateToLines(transport)

        // Then
        val value = sut.eventNavigateLines.getOrAwaitValue()
        assertEquals(value.getContentIfNotHandled(),transport)
    }
}