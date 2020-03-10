package com.quoders.apps.qmoves.lines

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.quoders.apps.qmoves.CoroutineTestRule
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
import org.junit.Rule
import org.junit.Test


class LinesViewModelTest {

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    val coroutineTestRule = CoroutineTestRule()


    private val transportName = "TransportXYZ"
    private lateinit var moqRepository: TransportRepository
    private lateinit var moqTransport: Transport
    private lateinit var sut: LinesViewModel

    @Before
    fun setUp() {
        moqTransport = mockk<Transport>()
        moqRepository = mockk<TransportRepository>()

        // Basic training
        every { moqTransport.name} returns transportName
        every { moqTransport.repository } returns moqRepository
        coEvery { moqRepository.getLines()} returns Result.Success(listOf())
    }

    @Test
    fun navigateToStops_invoked_navigateEventSet() = runBlockingTest {
        // Given
        sut = LinesViewModel(moqTransport)
        val line = Line("1", "orig-dest", Line.Direction.FORWARD, Line.LineType.REGULAR)

        // When
        sut.navigateToStops(line)

        // Then
        val value = sut.eventNavigateStops.getOrAwaitValue()
        assertThat(value.getContentIfNotHandled()).isEqualTo(line)
    }

    @Test
    fun getLines_validLineListInRepository_OnlyForwardLinesReturned() = runBlockingTest {
        // Given
        coEvery { moqRepository.getLines() } returns Result.Success(MoqLinesData.validLineList)

        // When
        sut = LinesViewModel(moqTransport)

        // Then
        val expectedLines =
            MoqLinesData.validLineList.filter { line -> line.direction == Line.Direction.FORWARD }
        val value = sut.lines.getOrAwaitValue()
        assertThat(value).hasSize(expectedLines.size)
        assertThat(value).isEqualTo(expectedLines)
    }

//
//    @Test
//    fun getTransportName() {
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