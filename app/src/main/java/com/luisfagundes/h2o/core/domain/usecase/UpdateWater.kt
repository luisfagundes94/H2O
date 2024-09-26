package com.luisfagundes.h2o.core.domain.usecase

import com.luisfagundes.h2o.core.domain.model.Water
import com.luisfagundes.h2o.core.domain.repository.WaterRepository
import javax.inject.Inject

interface UpdateWater {
    suspend operator fun invoke(water: Water)
}

class UpdateWaterImpl @Inject constructor(
    private val repository: WaterRepository,
): UpdateWater {
    override suspend operator fun invoke(water: Water) {
        if (water.consumed in 0f..water.goal) {
            repository.updateWater(water)
        }
    }
}
