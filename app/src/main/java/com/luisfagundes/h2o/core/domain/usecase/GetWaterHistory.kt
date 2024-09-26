package com.luisfagundes.h2o.core.domain.usecase

import com.luisfagundes.h2o.core.domain.model.Water
import com.luisfagundes.h2o.core.domain.repository.WaterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface GetWaterHistory {
    operator fun invoke(): Flow<List<Water>?>
}

class GetWaterHistoryImpl @Inject constructor(
    private val repository: WaterRepository,
) : GetWaterHistory {
    override operator fun invoke() = repository.getWaterHistory()
}
