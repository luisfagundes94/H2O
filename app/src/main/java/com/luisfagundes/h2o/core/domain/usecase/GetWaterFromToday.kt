package com.luisfagundes.h2o.core.domain.usecase

import com.luisfagundes.h2o.core.common.utils.getCurrentDate
import com.luisfagundes.h2o.core.domain.model.Water
import com.luisfagundes.h2o.core.domain.repository.UserDataRepository
import com.luisfagundes.h2o.core.domain.repository.WaterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

interface GetWaterFromToday {
    operator fun invoke(): Flow<Water>
}

class GetWaterFromTodayImpl @Inject constructor(
    private val waterRepository: WaterRepository,
    private val userDataRepository: UserDataRepository,
) : GetWaterFromToday {
    override operator fun invoke(): Flow<Water> {
        return combine(
            waterRepository.getWaterFrom(getCurrentDate()),
            userDataRepository.userData,
        ) { water, userData ->
            if (water == null) {
                val emptyWater =
                    Water.empty().copy(
                        date = getCurrentDate(),
                        goal = userData.waterGoal,
                    )
                waterRepository.addWater(emptyWater)
                emptyWater
            } else {
                water
            }
        }
    }
}
