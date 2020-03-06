package com.quoders.apps.qmoves.lines

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.quoders.apps.qmoves.CoroutineTestRule
import com.quoders.apps.qmoves.data.Line
import com.quoders.apps.qmoves.data.Transport
import com.quoders.apps.qmoves.getOrAwaitValue
import com.quoders.apps.qmoves.home.HomeViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule

class LinesViewModelTest {

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    private lateinit var sut: LinesViewModel

    @Before
    fun setUp() {
        sut = LinesViewModel(Transport("TransportTest"))
    }

    @Test
    fun navigateToStops_invoked_navigateEventSet() = runBlockingTest {
        // Given
        val line = Line("1", "orig-dest", Line.Direction.FORWARD, Line.LineType.REGULAR)

        // When
        sut.navigateToStops(line)

        // Then
        val value = sut.eventNavigateStops.getOrAwaitValue()
        assertEquals(value.getContentIfNotHandled(), line)
    }
//
//    @Test
//    fun getTransportName() {
//        TODO("To be implemented")
//    }
//
//    @Test
//    fun getLines() {
//        TODO("To be implemented")
//    }
//
//    @Test
//    fun getEmpty() {
//        TODO("To be implemented")
//    }
//
//    @Test
//    fun getDataLoading() {
//        TODO("To be implemented")
//    }
//
//    @Test
//    fun getSnackbarText() {
//        TODO("To be implemented")
//    }
//
//    @Test
//    fun getEventNavigateStops() {
//        TODO("To be implemented")
//    }
}