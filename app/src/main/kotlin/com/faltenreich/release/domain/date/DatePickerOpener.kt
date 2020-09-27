package com.faltenreich.release.domain.date

import androidx.fragment.app.FragmentManager
import org.threeten.bp.LocalDate

interface DatePickerOpener {

    fun openDatePicker(
        fragmentManager: FragmentManager,
        date: LocalDate?,
        onValueSelected: (LocalDate) -> Unit
    ) {
        val fragment = DatePickerFragment.newInstance(date)
        fragment.onValueChanged = onValueSelected
        fragment.show(fragmentManager, fragment.tag)
    }
}