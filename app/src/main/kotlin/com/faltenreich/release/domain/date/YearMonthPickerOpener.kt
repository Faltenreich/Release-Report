package com.faltenreich.release.domain.date

import androidx.fragment.app.FragmentManager
import org.threeten.bp.YearMonth

interface YearMonthPickerOpener {

    fun openYearMonthPicker(fragmentManager: FragmentManager, yearMonth: YearMonth? = null, onValueSelected: (YearMonth) -> Unit) {
        val fragment = YearMonthPickerFragment.newInstance(yearMonth)
        fragment.onValueChanged = onValueSelected
        fragment.show(fragmentManager, fragment.tag)
    }
}