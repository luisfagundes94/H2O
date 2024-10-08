package com.luisfagundes.h2o.core.data.preferences

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import com.luisfagundes.h2o.DarkThemeConfigProto
import com.luisfagundes.h2o.UserPreferences
import com.luisfagundes.h2o.copy
import com.luisfagundes.h2o.core.domain.model.DarkThemeConfig
import com.luisfagundes.h2o.core.domain.model.UserData
import com.luisfagundes.h2o.core.domain.model.WaterReminder
import javax.inject.Inject
import kotlinx.coroutines.flow.map

class H2oPreferencesDataSource @Inject constructor(
    private val userPreferences: DataStore<UserPreferences>
) {
    companion object {
        private const val DEFAULT_GOAL = 2000f
        private const val DEFAULT_START_HOUR = 9
        private const val DEFAULT_END_HOUR = 22
        private const val ZERO = 0
    }

    val userData = userPreferences.data.map { data ->
        UserData(
            darkThemeConfig = when (data.darkThemeConfig) {
                DarkThemeConfigProto.DARK_THEME_CONFIG_LIGHT -> DarkThemeConfig.LIGHT
                DarkThemeConfigProto.DARK_THEME_CONFIG_DARK -> DarkThemeConfig.DARK
                else -> DarkThemeConfig.FOLLOW_SYSTEM
            },
            useDynamicColor = data.useDynamicColor,
            notificationEnabled = data.notificationEnabled,
            goalOfTheDay = data.goalOfTheDay.takeIf { it > ZERO } ?: DEFAULT_GOAL,
            waterReminder = with(data.waterReminder) {
                WaterReminder(
                    startHour = startHour.takeIf { it > ZERO } ?: DEFAULT_START_HOUR,
                    endHour = endHour.takeIf { it > ZERO } ?: DEFAULT_END_HOUR,
                    interval = interval.takeIf { it > ZERO } ?: DEFAULT_END_HOUR
                )
            }
        )
    }

    suspend fun setDarkThemeConfig(darkThemeConfig: DarkThemeConfig) {
        updatePreferences { it.copy { this.darkThemeConfig = darkThemeConfig.toProto() } }
    }

    suspend fun setWaterReminder(waterReminder: WaterReminder) {
        updatePreferences {
            it.copy {
                this.waterReminder = it.waterReminder.copy {
                    this.startHour = waterReminder.startHour
                    this.endHour = waterReminder.endHour
                    this.interval = waterReminder.interval
                }
            }
        }
    }

    suspend fun setGoalOfTheDay(goal: Float) {
        updatePreferences { it.copy { this.goalOfTheDay = goal } }
    }

    suspend fun setDynamicColorPreference(useDynamicColor: Boolean) {
        updatePreferences { it.copy { this.useDynamicColor = useDynamicColor } }
    }

    suspend fun setNotificationEnabled(enabled: Boolean) {
        updatePreferences { it.copy { this.notificationEnabled = enabled } }
    }

    private suspend fun updatePreferences(update: (UserPreferences) -> UserPreferences) {
        try {
            userPreferences.updateData(update)
        } catch (e: IOException) {
            Log.e("H2oPreferencesDataSource", "Error updating preferences", e)
        }
    }

    private fun DarkThemeConfig.toProto(): DarkThemeConfigProto {
        return when (this) {
            DarkThemeConfig.FOLLOW_SYSTEM -> DarkThemeConfigProto.DARK_THEME_CONFIG_FOLLOW_SYSTEM
            DarkThemeConfig.LIGHT -> DarkThemeConfigProto.DARK_THEME_CONFIG_LIGHT
            DarkThemeConfig.DARK -> DarkThemeConfigProto.DARK_THEME_CONFIG_DARK
        }
    }
}
