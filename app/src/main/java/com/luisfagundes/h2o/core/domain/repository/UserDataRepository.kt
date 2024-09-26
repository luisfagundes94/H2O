package com.luisfagundes.h2o.core.domain.repository

import com.luisfagundes.h2o.core.domain.model.DarkThemeConfig
import com.luisfagundes.h2o.core.domain.model.UserData
import kotlinx.coroutines.flow.Flow

interface UserDataRepository {
    val userData: Flow<UserData>

    suspend fun setDarkModePreference(darkThemeConfig: DarkThemeConfig)

    suspend fun setGoalOfTheDay(goal: Float)

    suspend fun setWaterReminderInterval(interval: Float)

    suspend fun setDynamicColorPreference(useDynamicColor: Boolean)

    suspend fun setNotificationEnabled(enabled: Boolean)
}
