package com.luisfagundes.h2o.features.settings.model

data class GeneralSettings(
    val goalOfTheDay: Float,
    val startHour: Int,
    val endHour: Int,
    val interval: Int
)
