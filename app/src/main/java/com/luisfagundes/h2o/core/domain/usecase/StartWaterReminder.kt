package com.luisfagundes.h2o.core.domain.usecase

import com.luisfagundes.h2o.core.data.services.alarm.AlarmService
import com.luisfagundes.h2o.core.domain.model.HydrationReminder
import javax.inject.Inject

class StartWaterReminder @Inject constructor(
    private val alarmService: AlarmService
) {
    operator fun invoke(hydrationReminder: HydrationReminder) {
        alarmService.setRepeatingAlarm(hydrationReminder)
    }
}
