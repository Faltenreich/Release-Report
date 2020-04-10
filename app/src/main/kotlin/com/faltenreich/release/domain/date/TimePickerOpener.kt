package com.faltenreich.release.domain.date

import androidx.fragment.app.FragmentManager
import org.threeten.bp.LocalTime

interface TimePickerOpener {

    fun openTimePicker(fragmentManager: FragmentManager, time: LocalTime, onValueSelected: (LocalTime) -> Unit) {
        val fragment = TimePickerFragment.newInstance(time)
        fragment.onValueChanged = onValueSelected
        fragment.show(fragmentManager, fragment.tag)
    }
}