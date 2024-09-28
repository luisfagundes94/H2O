package com.luisfagundes.h2o.core.domain.model

data class WaterReminder(
    val startTime: Int,
    val endTime: Int,
    val intervalHours: Int
)
