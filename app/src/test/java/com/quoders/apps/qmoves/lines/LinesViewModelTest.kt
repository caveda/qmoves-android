package com.quoders.apps.qmoves.lines

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.quoders.apps.qmoves.CoroutineTestRule
import com.quoders.apps.qmoves.R
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

/**
 * Unit tests for LinesViewModel class
 */
class LinesViewModelTest {

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    val coroutineTestRule = CoroutineTestRule()


    private val transportName = "TransportXYZ"
    private lateinit var mockRepository: TransportRepository
    private lateinit var mockTransport: Transport
    private lateinit var sut: LinesViewModel

    @Before
    fun setUp() {
        // Mocked objects
        mockTransport = mockk()
        mockRepository = mockk()

        // Basic training
        every { mockTransport.name} returns transportName
        every { mockTransport.repository } returns mockRepository
        coEvery { mockRepository.getLines()} returns Result.Success(listOf())
    }

    @Test
    fun navigateToStops_invoked_navigateEventSet() = runBlockingTest {
        // Given
        sut = LinesViewModel(mockTransport)
        val line = Line("1", "orig-dest", Line.Direction.FORWARD, Line.LineType.REGULAR)

        // When
        sut.navigateToStops(line)

        // Then
        val value = sut.eventNavigateStops.getOrAwaitValue()
        assertThat(value.getContentIfNotHandled()).isEqualTo(line)
    }

    @Test
    fun getLines_noLinesInRepository_linesIsEmpty() = runBlockingTest {
        // When
        sut = LinesViewModel(mockTransport)

        // Then
        val value = sut.lines.getOrAwaitValue()
        assertThat(value).isEmpty()
    }

    @Test
    fun getLines_validLineListInRepository_OnlyForwardLinesReturned() = runBlockingTest {
        // Given
        coEvery { mockRepository.getLines() } returns Result.Success(MoqLinesData.validLineList)

        // When
        sut = LinesViewModel(mockTransport)

        // Then
        val expectedLines =
            MoqLinesData.validLineList.filter { line -> line.direction == Line.Direction.FORWARD }
        val value = sut.lines.getOrAwaitValue()
        assertThat(value).hasSize(expectedLines.size)
        assertThat(value).isEqualTo(expectedLines)
    }

    @Test
    fun getLines_errorLoadingLines_emptyLinesList() = runBlockingTest {
        // Given
        coEvery { mockRepository.getLines() } returns Result.Error(Exception("loading error"))

        // When
        sut = LinesViewModel(mockTransport)

        // Then
        val value = sut.lines.getOrAwaitValue()
        assertThat(value).isEmpty()
    }

    @Test
    fun getTransportName_validTransport_transportObjectNameReturned() = runBlockingTest {
        // Given
        sut = LinesViewModel(mockTransport)

        // Then
        assertThat(sut.transportName.getOrAwaitValue()).isEqualTo(transportName)
    }

    @Test
    fun getEmpty_noLinesInRepository_emptyIsTrue() = runBlockingTest {
        // When
        sut = LinesViewModel(mockTransport)

        // Then
        val value = sut.empty.getOrAwaitValue()
        assertThat(value).isTrue()
    }

    @Test
    fun getSnackbarText_errorLoadingLines_snackBarTextSetWithError() = runBlockingTest {
        // Given
        coEvery { mockRepository.getLines() } returns Result.Error(Exception("loading error"))

        // When
        sut =LinesViewModel(mockTransport)

        // Then
        val value = sut.snackbarText.getOrAwaitValue()
        assertThat(value.getContentIfNotHandled()).isEqualTo(R.string.error_loading_lines)
    }

    @Test
    fun getDataLoading_errorLoadingLines_dataLoadingIsError() {
        // Given
        coEvery { mockRepository.getLines() } returns Result.Error(Exception("loading error"))

        // When
        sut =LinesViewModel(mockTransport)

        // Then
        val value = sut.dataLoading.getOrAwaitValue()
        assertThat(value).isEqualTo(DataLoadingStatus.ERROR)
    }

    @Test
    fun getDataLoading_linesLoaded_dataLoadingIsDone() {
        // Given
        coEvery { mockRepository.getLines() } returns Result.Success(MoqLinesData.validLineList)

        // When
        sut =LinesViewModel(mockTransport)

        // Then
        val value = sut.dataLoading.getOrAwaitValue()
        assertThat(value).isEqualTo(DataLoadingStatus.DONE)
    }
}