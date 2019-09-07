package com.faltenreich.release.domain.reminder

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.Preference
import com.faltenreich.release.domain.date.TimePickerOpener
import com.faltenreich.release.domain.preference.UserPreferences
import org.threeten.bp.LocalTime

class ReminderTimePreference @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null
) : Preference(context, attributeSet), TimePickerOpener {

    override fun onClick() {
        super.onClick()
        openTimePicker()
    }

    private fun openTimePicker() {
        val fragmentManager = (context as? AppCompatActivity)?.supportFragmentManager ?: return
        val time = UserPreferences.reminderTime
        openTimePicker(fragmentManager, time, ::onTimeChanged)
    }

    private fun onTimeChanged(time: LocalTime) {
        UserPreferences.reminderTime = time
    }
}