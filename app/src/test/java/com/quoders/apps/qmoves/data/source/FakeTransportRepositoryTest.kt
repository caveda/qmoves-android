package com.quoders.apps.qmoves.data.source


import com.google.common.truth.Truth.assertThat
import com.quoders.apps.qmoves.data.Result.Success
import com.quoders.apps.qmoves.data.Transport
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test


class FakeTransportRepositoryTest {

    private lateinit var sut: FakeTransportRepository
    private val transport = Transport("Any")
    @Before
    fun createRepository() {
        sut = FakeTransportRepository()
    }

    @Test
    fun getLines_linesAvailable_successWithNonEmptyList() = runBlockingTest {

        // When
        val resultLines = sut.getLines(transport)

        // Then
        assertThat(resultLines is Success).isTrue()
        assertThat((resultLines as Success).data).isNotEmpty()
    }

    @Test
    fun getLines_linesAvailable_allLinesHasStops() = runBlockingTest {

        // When
        val resultLines = sut.getLines(transport)

        // Then
        (resultLines as Success).data.forEach {
            assertThat(it.stops).isNotEmpty()
        }
    }
}