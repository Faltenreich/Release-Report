package com.faltenreich.release.domain.reminder

import android.content.Context
import android.util.AttributeSet
import androidx.preference.Preference

class ReminderTimePreference @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null
) : Preference(context, attributeSet) {

    override fun onClick() {
        super.onClick()
        openTimePicker()
    }

    private fun openTimePicker() {

    }
}