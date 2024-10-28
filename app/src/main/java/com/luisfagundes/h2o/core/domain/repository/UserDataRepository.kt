package com.luisfagundes.h2o.core.domain.repository

import com.luisfagundes.h2o.core.domain.model.UserData
import com.luisfagundes.h2o.core.domain.model.WaterReminder
import kotlinx.coroutines.flow.Flow

interface UserDataRepository {
    val userData: Flow<UserData>
    suspend fun setAppLaunchedBefore()
    suspend fun setDarkMode(enabled: Boolean)
    suspend fun setDynamicColorPreference(useDynamicColor: Boolean)
    suspend fun setNotificationEnabled(enabled: Boolean)
    suspend fun setGoalOfTheDay(goal: Float)
    suspend fun setWaterReminder(waterReminder: WaterReminder)
}
