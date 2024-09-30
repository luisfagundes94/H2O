package com.luisfagundes.h2o.core.domain.model

data class UserData(
    val darkThemeConfig: DarkThemeConfig,
    val useDynamicColor: Boolean,
    val notificationEnabled: Boolean,
    val goalOfTheDay: Float,
    val waterReminder: WaterReminder
)
