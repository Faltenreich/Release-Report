package com.faltenreich.releaseradar.notification

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.faltenreich.releaseradar.extension.millis
import org.threeten.bp.LocalDate

object AlarmManager {

    private fun getAlarmManager(context: Context): AlarmManager {
        return context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    }

    fun scheduleAlarm(context: Context) {
        val start = LocalDate.now().plusDays(1).atTime(8, 0)
        val intent = Intent(context, NotificationAlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0)
        getAlarmManager(context).setInexactRepeating(AlarmManager.RTC, start.millis, AlarmManager.INTERVAL_DAY, pendingIntent)
    }
}