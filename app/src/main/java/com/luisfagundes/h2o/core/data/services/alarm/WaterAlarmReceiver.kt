package com.luisfagundes.h2o.core.data.services.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.luisfagundes.h2o.core.data.di.AlarmReceiverEntryPoint
import dagger.hilt.android.EntryPointAccessors

class WaterAlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action != WaterAlarmManagerImpl.ACTION_WATER_REMINDER) return

        context?.let {
            val systemTrayNotifier = EntryPointAccessors.fromApplication(
                context = it.applicationContext,
                entryPoint = AlarmReceiverEntryPoint::class.java
            ).getSystemTrayNotifier()

            systemTrayNotifier.remindToDrinkWater()
        }
    }
}
