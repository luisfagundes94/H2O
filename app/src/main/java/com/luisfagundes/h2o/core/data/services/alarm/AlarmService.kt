package com.luisfagundes.h2o.core.data.services.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.luisfagundes.h2o.core.domain.model.WaterReminder
import java.util.Calendar
import javax.inject.Inject

class AlarmService @Inject constructor(
    private val context: Context
) {
    fun setRepeatingAlarm(waterReminder: WaterReminder) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        val calendar = Calendar.getInstance()
        val currentHour = calendar.get(Calendar.HOUR_OF_DAY)

        if (currentHour < waterReminder.startTime || currentHour >= waterReminder.endTime) {
            calendar.add(Calendar.HOUR_OF_DAY, waterReminder.startTime)
            calendar.set(Calendar.MINUTE, 0)
            calendar.set(Calendar.SECOND, 0)
        } else {
            calendar.set(Calendar.MINUTE, 0)
            calendar.set(Calendar.SECOND, 0)
        }

        val intervalMillis = waterReminder.intervalHours * 60 * 60 * 1000L

        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            intervalMillis,
            pendingIntent
        )
    }
}
