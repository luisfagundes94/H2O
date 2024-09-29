package com.luisfagundes.h2o.core.domain.model

data class HydrationReminder(
    val startHour: Int,
    val endHour: Int,
    val interval: Int
)
