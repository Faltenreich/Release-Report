package com.faltenreich.release.domain.date

import androidx.fragment.app.FragmentManager

interface TimePickerOpener {
    fun openTimePicker(fragmentManager: FragmentManager, hour: Int, minute: Int, onValueSelected: (hour: Int, minute: Int) -> Unit) {
        val fragment = TimePickerFragment.newInstance(hour, minute)
        fragment.onValueChanged = onValueSelected
        fragment.show(fragmentManager, fragment.tag)
    }
}