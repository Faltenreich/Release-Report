package com.faltenreich.release.ui.logic.opener

import androidx.fragment.app.FragmentManager
import com.faltenreich.release.ui.fragment.DatePickerFragment
import org.threeten.bp.LocalDate

interface DatePickerOpener {
    fun openDatePicker(fragmentManager: FragmentManager, date: LocalDate? = null, onValueSelected: (LocalDate) -> Unit) {
        val fragment = DatePickerFragment.newInstance(date)
        fragment.onValueChanged = onValueSelected
        fragment.show(fragmentManager, fragment.tag)
    }
}