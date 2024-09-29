package com.luisfagundes.h2o.core.data.services.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.luisfagundes.h2o.core.data.services.notification.SystemTrayNotifier
import javax.inject.Inject

class AlarmReceiver @Inject constructor(
    private val systemTrayNotifier: SystemTrayNotifier
) : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        context?.let { systemTrayNotifier.remindToDrinkWater() }
    }
}
