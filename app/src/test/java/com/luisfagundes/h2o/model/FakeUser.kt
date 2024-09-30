package com.luisfagundes.h2o.model

import com.luisfagundes.h2o.core.domain.model.DarkThemeConfig
import com.luisfagundes.h2o.core.domain.model.UserData
import com.luisfagundes.h2o.core.domain.model.WaterReminder

val fakeUserData = UserData(
    darkThemeConfig = DarkThemeConfig.FOLLOW_SYSTEM,
    useDynamicColor = false,
    notificationEnabled = false,
    goalOfTheDay = 2000f,
    waterReminder = WaterReminder(
        startHour = 9,
        endHour = 10,
        interval = 3
    )
)
