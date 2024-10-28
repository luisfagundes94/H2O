package com.luisfagundes.h2o.core.testing.repository

import com.luisfagundes.h2o.core.data.preferences.H2oPreferencesDataSource
import com.luisfagundes.h2o.core.domain.model.DarkThemeConfig
import com.luisfagundes.h2o.core.domain.model.UserData
import com.luisfagundes.h2o.core.domain.model.WaterReminder
import com.luisfagundes.h2o.core.domain.repository.UserDataRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class FakeUserDataRepository @Inject constructor(
    private val h2oDataSource: H2oPreferencesDataSource
) : UserDataRepository {
    override val userData: Flow<UserData> = h2oDataSource.userData

    override suspend fun setDarkModePreference(darkThemeConfig: DarkThemeConfig) {
        h2oDataSource.setDarkThemeConfig(darkThemeConfig)
    }

    override suspend fun setDynamicColorPreference(useDynamicColor: Boolean) {
        h2oDataSource.setDynamicColorPreference(useDynamicColor)
    }

    override suspend fun setNotificationEnabled(enabled: Boolean) {
        h2oDataSource.setNotificationEnabled(enabled)
    }

    override suspend fun setGoalOfTheDay(goal: Float) {
        h2oDataSource.setGoalOfTheDay(goal)
    }

    override suspend fun setWaterReminder(waterReminder: WaterReminder) {
        h2oDataSource.setWaterReminder(waterReminder)
    }
}
