package com.luisfagundes.h2o.core.domain.usecase

import com.luisfagundes.h2o.core.common.utils.getCurrentDate
import com.luisfagundes.h2o.core.domain.model.Water
import com.luisfagundes.h2o.core.domain.repository.WaterRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

interface GetWaterHistory {
    operator fun invoke(): Flow<List<Water>?>
}

class GetWaterHistoryImpl @Inject constructor(
    private val repository: WaterRepository
) : GetWaterHistory {
    private companion object {
        const val ONE_WEEK = 7
    }

    override operator fun invoke(): Flow<List<Water>?> = flow {
        val waterHistory = repository.getWaterHistory().first()
        if (waterHistory != null && waterHistory.size > ONE_WEEK) {
            repository.deleteAllEntriesExceptToday(currentDate = getCurrentDate())
            emit(waterHistory.filter { it.date == getCurrentDate() })
        } else {
            emit(waterHistory)
        }
    }
}
