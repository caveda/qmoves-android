package com.quoders.apps.qmoves.data.source

import com.google.common.truth.Truth
import com.quoders.apps.qmoves.data.Result
import com.quoders.apps.qmoves.data.Transport
import com.quoders.apps.qmoves.data.source.local.DBFavorite
import com.quoders.apps.qmoves.data.source.local.TransportDatabaseDao
import com.quoders.apps.qmoves.data.source.remote.RemoteTransportService
import com.quoders.apps.qmoves.data.succeeded
import io.mockk.*
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

    @Test
    fun getLines_newRemoteData_remoteDataInsertedInDatabase() = runBlockingTest {
        // Given
        coEvery { mockRemoteTransport.isNewDataAvailable(mockTransport) } returns Result.Success(true)
        coEvery { mockRemoteTransport.fetchLines(mockTransport) } returns Result.Success(TestDataRemote.validRemoteLines)
        coEvery { mockDaoTransport.getLines(defaultTransportName) } returns TestDataDao.validDBLineList
        coEvery { mockDaoTransport.insertUpdateLines(defaultTransportName, any()) } just Runs

        // When
        sut.getLines(mockTransport)

        // Then
        coVerify { mockDaoTransport.insertUpdateLines(defaultTransportName, match {
            it.size == TestDataRemote.validRemoteLines.size
            })
        }
    }

    @Test
    fun getStops_DatabaseReturnedDBStopsOfLine_successWithNonEmptyListOfStops() = runBlockingTest {
        // Given
        val line = TestDataDao.validLineList[0]
        coEvery { mockDaoTransport.getLineWithStops(line.uuid) } returns TestDataDao.validDBLineWithStops
        coEvery { mockDaoTransport.getFavoritesOfLine(TestDataDao.validTransport.name,line.code) } returns emptyList()
        val expectedStops = TestDataDao.validListStops

        // When
        val resultStops = sut.getLineStops(TestDataDao.validTransport, line)

        // Then
        Truth.assertThat(resultStops.succeeded)
        val returnedStops = (resultStops as Result.Success).data
        Truth.assertThat(returnedStops).hasSize(expectedStops.size)
        Truth.assertThat(returnedStops).isEqualTo(expectedStops)
    }

    @Test
    fun getRoute_daoReturnedDBRouteOfLine_successWithNonEmptyListOfLocations() = runBlockingTest {
        // Given
        val line = TestDataDao.validLineList[0]
        coEvery { mockDaoTransport.getLineWithRoute(line.uuid) } returns TestDataDao.validDBLineWithRoute
        val expectedRoute = TestDataDao.validRoute

        // When
        val resultRoute = sut.getRoute(line)

        // Then
        Truth.assertThat(resultRoute.succeeded)
        val returnedRoute = (resultRoute as Result.Success).data
        Truth.assertThat(returnedRoute).hasSize(expectedRoute.size)
        Truth.assertThat(returnedRoute).isEqualTo(expectedRoute)
    }

    @Test
    fun getAllFavorites_daoReturnedDBFavoriteList_successWithNonEmptyListOfFavorites() = runBlockingTest {
        // Given
        val line = TestDataDao.validDBLineList
        coEvery { mockDaoTransport.getTransport(defaultTransportName) } returns TestDataDao.validDBTransport
        coEvery { mockDaoTransport.getLineOfAgency(defaultTransportName, TestDataDao.validFavoriteLineCode)} returns TestDataDao.validDBLineWithFavoriteStops
        coEvery { mockDaoTransport.getFavorites()} returns TestDataDao.validDBFavoriteList

        // When
        val resultFavorites = sut.getAllFavorites()

        // Then
        Truth.assertThat(resultFavorites.succeeded)
        val returnedFavorites = (resultFavorites as Result.Success).data
        Truth.assertThat(returnedFavorites).hasSize(TestDataDao.validDBFavoriteList.size)
        returnedFavorites.forEachIndexed { i, fav ->
            val expectedFavorite = TestDataDao.validDBFavoriteList[i]
            Truth.assertThat(expectedFavorite.lineCode).isEqualTo(fav.line.agencyId)
            Truth.assertThat(expectedFavorite.transportName).isEqualTo(fav.transport.name)
            Truth.assertThat(expectedFavorite.stopCode).isEqualTo(fav.stop.code)
        }
    }
}