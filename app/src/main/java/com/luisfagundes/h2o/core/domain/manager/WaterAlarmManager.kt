package com.luisfagundes.h2o.core.domain.manager

import com.luisfagundes.h2o.core.domain.model.WaterReminder

interface WaterAlarmManager {
    fun setRepeatingAlarm(waterReminder: WaterReminder)
    fun cancelRepeatingAlarm()
}
