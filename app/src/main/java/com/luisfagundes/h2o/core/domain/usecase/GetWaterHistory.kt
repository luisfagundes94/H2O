package com.luisfagundes.h2o.core.domain.usecase

import com.luisfagundes.h2o.core.domain.repository.WaterRepository
import javax.inject.Inject

class GetWaterHistory
    @Inject
    constructor(
        private val repository: WaterRepository,
    ) {
        operator fun invoke() = repository.getWaterHistory()
    }
