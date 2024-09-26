package com.luisfagundes.h2o.core.domain.usecase

import com.luisfagundes.h2o.core.common.result.Result
import com.luisfagundes.h2o.core.domain.model.Water
import com.luisfagundes.h2o.core.domain.repository.WaterRepository
import javax.inject.Inject

class UpdateWater @Inject constructor(
    private val repository: WaterRepository
) {
    suspend operator fun invoke(water: Water): Result<Unit> {
        return if (water.consumed in 0f..water.goal) {
            repository.updateWater(water)
        } else {
            Result.Empty
        }
    }
}