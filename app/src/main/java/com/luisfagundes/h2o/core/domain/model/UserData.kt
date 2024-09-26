package com.luisfagundes.h2o.core.domain.model

data class UserData(
    val darkThemeConfig: DarkThemeConfig,
    val useDynamicColor: Boolean,
    val waterGoal: Float,
    val waterReminderInterval: Float,
    val notificationEnabled: Boolean,
)