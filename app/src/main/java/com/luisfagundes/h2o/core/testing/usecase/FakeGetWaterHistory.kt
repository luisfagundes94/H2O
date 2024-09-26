package com.luisfagundes.h2o.core.testing.usecase

import com.luisfagundes.h2o.core.domain.model.Water
import com.luisfagundes.h2o.core.domain.usecase.GetWaterHistory
import kotlinx.coroutines.channels.BufferOverflow.DROP_OLDEST
import kotlinx.coroutines.flow.MutableSharedFlow

class FakeGetWaterHistory : GetWaterHistory {
    private val waterHistoryFlow: MutableSharedFlow<List<Water>?> =
        MutableSharedFlow(replay = 1, onBufferOverflow = DROP_OLDEST)

    override fun invoke() = waterHistoryFlow

    fun emitWaterHistory(waterHistory: List<Water>) {
        waterHistoryFlow.tryEmit(waterHistory)
    }
}
