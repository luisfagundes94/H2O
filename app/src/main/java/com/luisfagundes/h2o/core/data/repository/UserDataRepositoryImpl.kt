package com.luisfagundes.h2o.core.data.repository

import com.luisfagundes.h2o.core.data.preferences.H2oPreferencesDataSource
import com.luisfagundes.h2o.core.domain.model.DarkThemeConfig
import com.luisfagundes.h2o.core.domain.model.UserData
import com.luisfagundes.h2o.core.domain.repository.UserDataRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class UserDataRepositoryImpl @Inject constructor(
    private val preferencesDataSource: H2oPreferencesDataSource
) : UserDataRepository {
    override val userData: Flow<UserData>
        get() = preferencesDataSource.userData

    override suspend fun setDarkModePreference(darkThemeConfig: DarkThemeConfig) {
        preferencesDataSource.setDarkThemeConfig(darkThemeConfig)
    }

    override suspend fun setGoalOfTheDay(goal: Float) {
        preferencesDataSource.setGoalOfTheDay(goal)
    }

    override suspend fun setWaterReminderInterval(interval: Float) {
        preferencesDataSource.setWaterReminderInterval(interval)
    }

    override suspend fun setDynamicColorPreference(useDynamicColor: Boolean) {
        preferencesDataSource.setDynamicColorPreference(useDynamicColor)
    }

    override suspend fun setNotificationEnabled(enabled: Boolean) {
        preferencesDataSource.setNotificationEnabled(enabled)
    }
}
