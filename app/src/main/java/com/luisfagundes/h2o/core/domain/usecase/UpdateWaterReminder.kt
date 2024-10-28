package com.luisfagundes.h2o.core.domain.usecase

import com.luisfagundes.h2o.core.domain.manager.WaterAlarmManager
import com.luisfagundes.h2o.core.domain.model.WaterReminder
import javax.inject.Inject

class UpdateWaterReminder @Inject constructor(
    private val alarmService: WaterAlarmManager
) {
    operator fun invoke(waterReminder: WaterReminder) {
        alarmService.setRepeatingAlarm(waterReminder)
    }
}
