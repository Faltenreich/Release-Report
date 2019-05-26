package com.faltenreich.release.reminder.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import com.faltenreich.release.extension.className
import com.faltenreich.release.extension.millis
import com.faltenreich.release.reminder.ReminderService
import org.threeten.bp.LocalDateTime

object AlarmManager {

    private fun getAlarmManager(context: Context): AlarmManager {
        return context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    }

    fun scheduleAlarm(context: Context) {
        Log.d(className, "Scheduling alarm")
        val alarmManager = getAlarmManager(context)
        // val start = LocalDate.now().plusDays(1).atTime(8, 0)
        val start = LocalDateTime.now().plusSeconds(1)
        val intent = Intent(context, ReminderService::class.java)
        val pendingIntent = PendingIntent.getService(context,
            ReminderService.SERVICE_ID, intent, PendingIntent.FLAG_CANCEL_CURRENT)
        alarmManager.setInexactRepeating(AlarmManager.RTC, start.millis, AlarmManager.INTERVAL_DAY, pendingIntent)
    }
}
