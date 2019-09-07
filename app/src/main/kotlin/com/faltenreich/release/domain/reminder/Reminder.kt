package com.faltenreich.release.domain.reminder

import android.content.Context
import android.util.Log
import com.faltenreich.release.base.date.print
import com.faltenreich.release.base.log.tag
import com.faltenreich.release.domain.preference.UserPreferences
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import org.threeten.bp.temporal.ChronoUnit

object Reminder {

    fun refresh(context: Context) {
        if (UserPreferences.reminderIsEnabled) {
            enqueue(context)
        } else {
            cancel(context)
        }
    }

    private fun enqueue(context: Context) {
        val now = LocalDateTime.now()
        val today = LocalDate.now()

        val reminderTime = UserPreferences.reminderTime
        val remindToday = now.toLocalTime().isBefore(reminderTime)
        val reminderDate = if (remindToday) today else today.plusDays(1)

        val target = LocalDateTime.of(reminderDate, reminderTime)
        val delayInMillis = ChronoUnit.MILLIS.between(now, target)

        ReminderWorker.enqueue(context, delayInMillis)
        Log.d(tag, "Enqueued reminder for ${reminderTime.print()}")
    }

    private fun cancel(context: Context) {
        ReminderWorker.cancel(context)
        Log.d(tag, "Cancelled reminder")
    }

    fun remind(context: Context) {
        SubscriptionReminder().remind(context)
    }
}