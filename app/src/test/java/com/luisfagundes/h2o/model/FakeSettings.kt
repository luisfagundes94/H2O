package com.luisfagundes.h2o.model

import com.luisfagundes.h2o.features.settings.model.AppSettings
import com.luisfagundes.h2o.features.settings.model.GeneralSettings

val fakeGeneralSettings = GeneralSettings(
    goalOfTheDay = 2000f,
    startHour = 9,
    endHour = 10,
    interval = 3
)

val fakeAppSettings = AppSettings(
    darkModeEnabled = true
)
