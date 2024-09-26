package com.luisfagundes.h2o.usecase

import com.luisfagundes.h2o.core.domain.model.Water
import com.luisfagundes.h2o.core.domain.repository.WaterRepository
import com.luisfagundes.h2o.core.domain.usecase.GetWaterHistoryImpl
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import com.luisfagundes.h2o.model.fakeWater
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class GetWaterHistoryTest {

    private val repository = mockk<WaterRepository>()
    private val getWaterHistory = GetWaterHistoryImpl(repository)

    @Test
    fun `invoke returns water history`() = runTest {
        val waterHistory = listOf(
            fakeWater.copy(consumed = 500f, goal = 2000f),
            fakeWater.copy(consumed = 900f, goal = 5000f),
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
}