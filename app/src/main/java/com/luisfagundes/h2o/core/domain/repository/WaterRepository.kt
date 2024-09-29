package com.luisfagundes.h2o.core.domain.repository

import com.luisfagundes.h2o.core.common.result.Result
import com.luisfagundes.h2o.core.domain.model.Water
import kotlinx.coroutines.flow.Flow

interface WaterRepository {
    fun getWaterFrom(date: String): Flow<Water?>
    fun getWaterHistory(): Flow<List<Water>?>
    suspend fun addWater(water: Water)
    suspend fun updateWater(water: Water): Result<Unit>
    suspend fun deleteWater(water: Water)
    suspend fun deleteOldEntries(currentDate: String)
}
