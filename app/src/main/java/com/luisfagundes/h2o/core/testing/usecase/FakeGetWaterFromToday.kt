package com.luisfagundes.h2o.core.testing.usecase

import com.luisfagundes.h2o.core.common.utils.getCurrentDate
import com.luisfagundes.h2o.core.domain.model.UserData
import com.luisfagundes.h2o.core.domain.model.Water
import com.luisfagundes.h2o.core.domain.usecase.GetWaterFromToday
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow

class FakeGetWaterFromToday : GetWaterFromToday {
    private val waterFlow: MutableSharedFlow<Water> =
        MutableSharedFlow(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    override fun invoke() = waterFlow

    fun emitWater(water: Water?, userData: UserData) {
        if (water == null) {
            val emptyWater = Water.empty().copy(
                date = getCurrentDate(),
                goal = userData.goalOfTheDay
            )
            waterFlow.tryEmit(emptyWater)
        } else {
            waterFlow.tryEmit(water)
        }
    }
}
