package com.luisfagundes.h2o.core.domain.usecase

import com.luisfagundes.h2o.core.common.utils.getCurrentDate
import com.luisfagundes.h2o.core.domain.model.UserData
import com.luisfagundes.h2o.core.domain.model.Water
import com.luisfagundes.h2o.core.domain.repository.UserDataRepository
import com.luisfagundes.h2o.core.domain.repository.WaterRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

interface GetWaterFromToday {
    operator fun invoke(): Flow<Water>
}

class GetWaterFromTodayImpl @Inject constructor(
    private val waterRepository: WaterRepository,
    private val userDataRepository: UserDataRepository
) : GetWaterFromToday {
    override fun invoke(): Flow<Water> = combine(
        waterRepository.getWaterFrom(getCurrentDate()),
        userDataRepository.userData
    ) { water, userData ->
        water?.copy(goal = userData.waterGoal) ?: createAndAddEmptyWater(userData)
    }

    private suspend fun createAndAddEmptyWater(userData: UserData): Water {
        val emptyWater = Water.empty().copy(
            date = getCurrentDate(),
            goal = userData.waterGoal
        )
        waterRepository.addWater(emptyWater)
        return emptyWater
    }
}
