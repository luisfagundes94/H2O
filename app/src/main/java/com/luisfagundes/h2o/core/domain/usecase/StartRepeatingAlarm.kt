package com.luisfagundes.h2o.core.domain.usecase

import com.luisfagundes.h2o.core.data.services.alarm.AlarmService
import com.luisfagundes.h2o.core.domain.model.WaterReminder
import javax.inject.Inject

class StartRepeatingAlarm @Inject constructor(
    private val alarmService: AlarmService
) {
    operator fun invoke(waterReminder: WaterReminder) {
        alarmService.setRepeatingAlarm(waterReminder)
    }
}
