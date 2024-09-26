package com.luisfagundes.h2o.model

import com.luisfagundes.h2o.core.domain.model.DarkThemeConfig
import com.luisfagundes.h2o.core.domain.model.UserData

val fakeUserData =
    UserData(
        darkThemeConfig = DarkThemeConfig.FOLLOW_SYSTEM,
        useDynamicColor = false,
        waterGoal = 2000f,
        waterReminderInterval = 3f,
        notificationEnabled = false
    )
