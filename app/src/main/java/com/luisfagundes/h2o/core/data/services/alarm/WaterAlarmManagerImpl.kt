package com.luisfagundes.h2o.core.data.services.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.luisfagundes.h2o.core.domain.manager.WaterAlarmManager
import com.luisfagundes.h2o.core.domain.model.WaterReminder
import java.util.Calendar
import javax.inject.Inject

private const val REQUEST_CODE = 0
private const val MILLISECONDS_IN_HOUR = 60 * 60 * 1000L

class WaterAlarmManagerImpl @Inject constructor(
    private val context: Context
) : WaterAlarmManager {
    companion object {
        const val ACTION_WATER_REMINDER = "com.luisfagundes.h2o.ACTION_WATER_REMINDER"
    }

    override fun setRepeatingAlarm(waterReminder: WaterReminder) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, WaterAlarmReceiver::class.java).apply {
            action = ACTION_WATER_REMINDER
            putExtra("startHour", waterReminder.startHour)
            putExtra("endHour", waterReminder.endHour)
            putExtra("interval", waterReminder.interval)
        }
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            REQUEST_CODE,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        val calendar = Calendar.getInstance()
        val currentHour = calendar.get(Calendar.HOUR_OF_DAY)

        if (currentHour < waterReminder.startHour || currentHour >= waterReminder.endHour) {
            calendar.set(Calendar.HOUR_OF_DAY, waterReminder.startHour.toInt())
        }

        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)

        val intervalMillis = waterReminder.interval * MILLISECONDS_IN_HOUR

        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            80_000L,
            pendingIntent
        )
    }

    override fun cancelRepeatingAlarm() {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, WaterAlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            REQUEST_CODE,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        alarmManager.cancel(pendingIntent)
    }
}
