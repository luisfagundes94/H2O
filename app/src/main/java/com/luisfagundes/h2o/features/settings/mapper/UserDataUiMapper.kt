package com.luisfagundes.h2o.features.settings.mapper

import com.luisfagundes.h2o.core.domain.model.UserData
import com.luisfagundes.h2o.features.settings.SettingsUiState
import com.luisfagundes.h2o.features.settings.model.AppSettings
import com.luisfagundes.h2o.features.settings.model.GeneralSettings

fun UserData.toSettingsUiState(): SettingsUiState.Success {
    return SettingsUiState.Success(
        generalSettings = GeneralSettings(
            goalOfTheDay = this.goalOfTheDay,
            startHour = this.waterReminder.startHour,
            endHour = this.waterReminder.endHour,
            interval = this.waterReminder.interval
        ),
        appSettings = AppSettings(
            notificationEnabled = this.notificationEnabled
        )
    )
}
