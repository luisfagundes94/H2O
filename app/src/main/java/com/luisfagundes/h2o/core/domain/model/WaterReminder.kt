package com.luisfagundes.h2o.core.domain.model

data class WaterReminder(
    val startHour: Int,
    val endHour: Int,
    val interval: Int
)
