package com.luisfagundes.h2o.core.data.repository

import com.luisfagundes.h2o.core.common.result.Result
import com.luisfagundes.h2o.core.data.database.WaterDao
import com.luisfagundes.h2o.core.data.mapper.WaterMapper.toDomainModel
import com.luisfagundes.h2o.core.data.mapper.WaterMapper.toEntityModel
import com.luisfagundes.h2o.core.domain.model.Water
import com.luisfagundes.h2o.core.domain.repository.WaterRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class WaterRepositoryImpl @Inject constructor(
    private val dao: WaterDao
) : WaterRepository {
    override fun getWaterFrom(date: String): Flow<Water?> {
        return dao.getWaterFrom(date).map { entity ->
            entity?.toDomainModel()
        }
    }

    override fun getWaterHistory(): Flow<List<Water>?> {
        return dao.getWaterHistory().map { list ->
            list?.map { it.toDomainModel() }
        }
    }

    override suspend fun addWater(water: Water) {
        dao.insertWater(water.toEntityModel())
    }

    override suspend fun updateWater(water: Water): Result<Unit> {
        return try {
            dao.updateWater(water.toEntityModel())
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun deleteWater(water: Water) {
        dao.deleteWater(water.toEntityModel())
    }
}
