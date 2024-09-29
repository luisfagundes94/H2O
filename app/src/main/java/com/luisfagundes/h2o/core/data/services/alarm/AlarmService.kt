package com.luisfagundes.h2o.core.data.services.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.luisfagundes.h2o.core.domain.model.HydrationReminder
import java.util.Calendar
import javax.inject.Inject

class AlarmService @Inject constructor(
    private val context: Context
) {
    companion object {
        private const val REQUEST_CODE = 0
        private const val MILLISECONDS_IN_HOUR = 60 * 60 * 1000L
    }

    fun setRepeatingAlarm(hydrationReminder: HydrationReminder) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            REQUEST_CODE,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        val calendar = Calendar.getInstance()
        val currentHour = calendar.get(Calendar.HOUR_OF_DAY)

        if (currentHour < hydrationReminder.startHour || currentHour >= hydrationReminder.endHour) {
            calendar.set(Calendar.HOUR_OF_DAY, hydrationReminder.startHour)
        }

        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)

        val intervalMillis = hydrationReminder.interval * MILLISECONDS_IN_HOUR

        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            intervalMillis,
            pendingIntent
        )
    }

    fun cancelRepeatingAlarm() {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        alarmManager.cancel(pendingIntent)
    }
}
