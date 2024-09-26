package com.luisfagundes.h2o.repository

import com.luisfagundes.h2o.core.common.result.Result
import com.luisfagundes.h2o.core.data.database.WaterDao
import com.luisfagundes.h2o.core.data.mapper.WaterMapper.toDomainModel
import com.luisfagundes.h2o.core.data.mapper.WaterMapper.toEntityModel
import com.luisfagundes.h2o.core.data.repository.WaterRepositoryImpl
import com.luisfagundes.h2o.model.fakeWater
import com.luisfagundes.h2o.model.fakeWaterEntity
import com.luisfagundes.h2o.model.fakeWaterEntityList
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class WaterRepositoryTest {
    private val dao: WaterDao = mockk()
    private val repository = WaterRepositoryImpl(dao)

    @Test
    fun getWaterFrom_returnsWater() =
        runTest {
            val date = "2023-10-01"
            coEvery { dao.getWaterFrom(date) } returns flowOf(fakeWaterEntity)

            val result = repository.getWaterFrom(date).first()

            assertEquals(fakeWaterEntity.toDomainModel(), result)
        }

    @Test
    fun getWaterFrom_returnsNullWhenNoData() =
        runTest {
            val date = "2023-10-01"
            coEvery { dao.getWaterFrom(date) } returns flowOf(null)

            val result = repository.getWaterFrom(date).first()

            assertEquals(null, result)
        }

    @Test
    fun getWaterHistory_returnsWaterList() =
        runTest {
            coEvery { dao.getWaterHistory() } returns flowOf(fakeWaterEntityList)

            val result = repository.getWaterHistory().first()

            assertEquals(fakeWaterEntityList.map { it.toDomainModel() }, result)
        }

    @Test
    fun getWaterHistory_returnsEmptyListWhenNoData() =
        runTest {
            coEvery { dao.getWaterHistory() } returns flowOf(emptyList())

            val result = repository.getWaterHistory().first()

            assertTrue(result.isNullOrEmpty())
        }

    @Test
    fun addWater_insertsWater() =
        runTest {
            coEvery { dao.insertWater(fakeWater.toEntityModel()) } returns Unit

            repository.addWater(fakeWater)

            coVerify { dao.insertWater(fakeWater.toEntityModel()) }
        }

    @Test
    fun updateWater_updatesWaterSuccessfully() =
        runTest {
            coEvery { dao.updateWater(fakeWater.toEntityModel()) } returns Unit

            val result = repository.updateWater(fakeWater)

            assertTrue(result is Result.Success)
        }

    @Test
    fun updateWater_returnsErrorOnException() =
        runTest {
            val exception = Exception("Update failed")
            coEvery { dao.updateWater(fakeWater.toEntityModel()) } throws exception

            val result = repository.updateWater(fakeWater)

            assertTrue(result is Result.Error)
            assertEquals(exception, (result as Result.Error).exception)
        }

    @Test
    fun deleteWater_deletesWater() =
        runTest {
            coEvery { dao.deleteWater(fakeWater.toEntityModel()) } returns Unit

            repository.deleteWater(fakeWater)

            coVerify { dao.deleteWater(fakeWater.toEntityModel()) }
        }
}
