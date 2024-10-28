package com.luisfagundes.h2o.core.data.preferences

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import com.luisfagundes.h2o.UserPreferences
import com.luisfagundes.h2o.copy
import com.luisfagundes.h2o.core.common.utils.AppConstants.DEFAULT_END_HOUR
import com.luisfagundes.h2o.core.common.utils.AppConstants.DEFAULT_GOAL
import com.luisfagundes.h2o.core.common.utils.AppConstants.DEFAULT_INTERVAL
import com.luisfagundes.h2o.core.common.utils.AppConstants.DEFAULT_START_HOUR
import com.luisfagundes.h2o.core.domain.model.UserData
import com.luisfagundes.h2o.core.domain.model.WaterReminder
import javax.inject.Inject
import kotlinx.coroutines.flow.map

class H2oPreferencesDataSource @Inject constructor(
    private val userPreferences: DataStore<UserPreferences>
) {
    companion object {
        private const val ZERO = 0
    }

    val userData = userPreferences.data.map { data ->
        UserData(
            appLaunchedBefore = data.appLaunchedBefore,
            darkModeEnabled = data.darkModeEnabled,
            useDynamicColor = data.useDynamicColor,
            notificationEnabled = data.notificationEnabled,
            goalOfTheDay = data.goalOfTheDay.takeIf { it > ZERO } ?: DEFAULT_GOAL,
            waterReminder = with(data.waterReminder) {
                WaterReminder(
                    startHour = startHour.takeIf { it > ZERO } ?: DEFAULT_START_HOUR,
                    endHour = endHour.takeIf { it > ZERO } ?: DEFAULT_END_HOUR,
                    interval = interval.takeIf { it > ZERO } ?: DEFAULT_INTERVAL
                )
            }
        )
    }

    suspend fun setAppLaunchedBefore() {
        updatePreferences { it.copy { this.appLaunchedBefore = true } }
    }

    suspend fun setDarkMode(enabled: Boolean) {
        updatePreferences { it.copy { this.darkModeEnabled = enabled } }
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
}
