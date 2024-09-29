package com.luisfagundes.h2o.usecase

import com.luisfagundes.h2o.core.common.utils.getCurrentDate
import com.luisfagundes.h2o.core.domain.model.Water
import com.luisfagundes.h2o.core.domain.repository.WaterRepository
import com.luisfagundes.h2o.core.domain.usecase.GetWaterHistoryImpl
import com.luisfagundes.h2o.model.fakeWater
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class GetWaterHistoryTest {

    private val repository = mockk<WaterRepository>()
    private val getWaterHistory = GetWaterHistoryImpl(repository)

    private val currentDate = getCurrentDate()

    @Test
    fun `invoke returns water history`() = runTest {
        val waterHistory = listOf(
            fakeWater.copy(consumed = 500f, goal = 2000f),
            fakeWater.copy(consumed = 900f, goal = 5000f)
        )
        coEvery { repository.getWaterHistory() } returns flowOf(waterHistory)

        val result = getWaterHistory.invoke().first()

        assertEquals(waterHistory, result)
    }

    @Test
    fun `invoke returns empty list when no water history`() = runTest {
        coEvery { repository.getWaterHistory() } returns flowOf(emptyList())

        val result = getWaterHistory.invoke().first()

        assertEquals(emptyList<Water>(), result)
    }

    @Test
    fun `invoke returns null when repository returns null`() = runTest {
        coEvery { repository.getWaterHistory() } returns flowOf(null)

        val result = getWaterHistory.invoke().toList()

        assertEquals(listOf(null), result)
    }

    @Test
    fun `deletes all entries except today when water history size is greater than one week`() =
        runTest {
            val waterHistory = MutableList(7) { fakeWater.copy(date = "2023-10-01") }
            waterHistory.add(fakeWater.copy(date = currentDate))

            coEvery { repository.getWaterHistory() } returns flowOf(waterHistory)
            coEvery { repository.deleteAllEntriesExceptToday(any()) } returns Unit

            val result = getWaterHistory.invoke().first()

            assertEquals(listOf(fakeWater.copy(date = currentDate)), result)
            coVerify { repository.deleteAllEntriesExceptToday(currentDate) }
        }

    @Test
    fun `invoke returns water history when size is less than or equal to one week`() = runTest {
        val waterHistory = List(7) { fakeWater.copy(date = currentDate) }
        coEvery { repository.getWaterHistory() } returns flowOf(waterHistory)

        val result = getWaterHistory.invoke().first()

        assertEquals(waterHistory, result)
    }

    @Test
    fun `invoke returns empty list when water history is empty`() = runTest {
        coEvery { repository.getWaterHistory() } returns flowOf(emptyList())

        val result = getWaterHistory.invoke().first()

        assertEquals(emptyList<Water>(), result)
    }

    @Test
    fun `invoke returns null when water history is null`() = runTest {
        coEvery { repository.getWaterHistory() } returns flowOf(null)

        val result = getWaterHistory.invoke().toList()

        assertEquals(listOf(null), result)
    }
}
