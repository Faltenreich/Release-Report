package com.faltenreich.release.domain.reminder

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.Preference
import com.faltenreich.release.domain.date.TimePickerOpener
import org.threeten.bp.LocalDateTime

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
        // TODO: Get time from preference
        val now = LocalDateTime.now()
        openTimePicker(fragmentManager, now.hour, now.minute, ::onTimeChanged)
    }

    private fun onTimeChanged(hour: Int, minute: Int) {
        // TODO: Set time to preference
    }
}