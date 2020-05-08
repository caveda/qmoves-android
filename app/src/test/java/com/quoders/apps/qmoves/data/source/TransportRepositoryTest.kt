package com.quoders.apps.qmoves.data.source

import com.google.common.truth.Truth
import com.quoders.apps.qmoves.data.Result
import com.quoders.apps.qmoves.data.Transport
import com.quoders.apps.qmoves.data.source.local.TransportDatabaseDao
import com.quoders.apps.qmoves.data.source.remote.RemoteTransportService
import com.quoders.apps.qmoves.data.succeeded
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

class TransportRepositoryTest {

    private lateinit var sut: TransportRepositoryImpl
    private lateinit var mockDaoTransport: TransportDatabaseDao
    private lateinit var mockRemoteTransport: RemoteTransportService
    private lateinit var mockTransport: Transport
    private val defaultTransportName = TestDataDao.transportName

    @Before
    fun setUp() {
        mockDaoTransport = mockk()
        mockRemoteTransport = mockk()
        mockTransport = mockk()

        every { mockTransport.name} returns defaultTransportName
        coEvery { mockRemoteTransport.isNewDataAvailable(mockTransport) } returns Result.Success(false)
        sut = TransportRepositoryImpl(mockDaoTransport,mockRemoteTransport)
    }

    @Test
    fun getLines_databaseReturnedDBLines_remoteDataChecked() = runBlockingTest {
        // Given
        coEvery { mockDaoTransport.getLines(defaultTransportName) } returns TestDataDao.validDBLineList

        // When
        sut.getLines(mockTransport)

        // Then
        coVerify { mockRemoteTransport.isNewDataAvailable(mockTransport) }
    }

    @Test
    fun getLines_DatabaseReturnedDBLines_successWithNonEmptyListOfLines() = runBlockingTest {
        // Given
        coEvery { mockDaoTransport.getLines(defaultTransportName) } returns TestDataDao.validDBLineList
        val expectedLines = TestDataDao.validLineList

        // When
        val resultLines = sut.getLines(mockTransport)

        // Then
        Truth.assertThat(resultLines.succeeded)
        val returnedLines = (resultLines as Result.Success).data
        Truth.assertThat(returnedLines).hasSize(expectedLines.size)
        Truth.assertThat(returnedLines).isEqualTo(expectedLines)
    }
}