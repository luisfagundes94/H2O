package com.luisfagundes.h2o.usecase

import com.luisfagundes.h2o.core.common.result.Result
import com.luisfagundes.h2o.core.domain.repository.WaterRepository
import com.luisfagundes.h2o.core.domain.usecase.UpdateWaterImpl
import com.luisfagundes.h2o.model.fakeWater
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class UpdateWaterImplTest {

    private val repository = mockk<WaterRepository>()
    private val updateWater = UpdateWaterImpl(repository)

    @Test
    fun `invoke with valid water amount returns success`() = runTest {
        val water = fakeWater.copy(consumed = 500f, goal = 2000f)
        coEvery { repository.updateWater(water) } returns Result.Success(Unit)

        val result = updateWater.invoke(water)

        assertEquals(Result.Success(Unit), result)
        coVerify { repository.updateWater(water) }
    }

    @Test
    fun `invoke with consumed water less than zero returns error`() = runTest {
        val water = fakeWater.copy(consumed = -100f, goal = 2000f)

        val result = updateWater.invoke(water)

        assertEquals("Invalid water amount", (result as Result.Error).exception.message)
        coVerify(exactly = 0) { repository.updateWater(water) }
    }

    @Test
    fun `invoke with consumed water greater than goal returns error`() = runTest {
        val water = fakeWater.copy(consumed = 2500f, goal = 2000f)

        val result = updateWater.invoke(water)

        assertEquals("Invalid water amount", (result as Result.Error).exception.message)
        coVerify(exactly = 0) { repository.updateWater(water) }
    }
}