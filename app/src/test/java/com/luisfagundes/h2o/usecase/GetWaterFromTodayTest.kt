package com.luisfagundes.h2o.usecase

import com.luisfagundes.h2o.core.common.utils.getCurrentDate
import com.luisfagundes.h2o.core.domain.repository.UserDataRepository
import com.luisfagundes.h2o.core.domain.repository.WaterRepository
import com.luisfagundes.h2o.core.domain.usecase.GetWaterFromToday
import com.luisfagundes.h2o.core.domain.usecase.GetWaterFromTodayImpl
import com.luisfagundes.h2o.model.fakeUserData
import com.luisfagundes.h2o.model.fakeWater
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GetWaterFromTodayTest {
    private lateinit var getWaterFromToday: GetWaterFromToday
    private val waterRepository: WaterRepository = mockk()
    private val userDataRepository: UserDataRepository = mockk()

    @Before
    fun setUp() {
        getWaterFromToday = GetWaterFromTodayImpl(waterRepository, userDataRepository)
    }

    @Test
    fun returnsWaterFromToday_whenWaterExists() = runTest {
        coEvery { waterRepository.getWaterFrom(any()) } returns flowOf(fakeWater)
        coEvery { userDataRepository.userData } returns flowOf(fakeUserData)

        val result = getWaterFromToday().first()

        assert(result == fakeWater)
    }

    @Test
    fun returnsEmptyWaterWithGoal_whenWaterDoesNotExist() = runTest {
        coEvery { waterRepository.getWaterFrom(any()) } returns flowOf(null)
        coEvery { userDataRepository.userData } returns flowOf(fakeUserData)
        coEvery { waterRepository.addWater(any()) } returns Unit

        val result = getWaterFromToday().first()

        assert(result.goal == fakeUserData.goalOfTheDay)
        coVerify { waterRepository.addWater(any()) }
    }

    @Test
    fun returnsEmptyWaterWithCurrentDate_whenWaterDoesNotExist() = runTest {
        coEvery { waterRepository.getWaterFrom(any()) } returns flowOf(null)
        coEvery { userDataRepository.userData } returns flowOf(fakeUserData)
        coEvery { waterRepository.addWater(any()) } returns Unit

        val result = getWaterFromToday().first()

        assert(result.date == getCurrentDate())
        coVerify { waterRepository.addWater(any()) }
    }
}
