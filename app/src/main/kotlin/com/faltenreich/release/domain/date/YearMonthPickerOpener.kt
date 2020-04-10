package com.faltenreich.release.domain.date

import androidx.fragment.app.FragmentManager
import com.faltenreich.release.domain.date.YearMonthPickerFragment
import org.threeten.bp.YearMonth

interface YearMonthPickerOpener {

    fun openYearMonthPicker(fragmentManager: FragmentManager, yearMonth: YearMonth? = null, onValueSelected: (YearMonth) -> Unit) {
        val fragment = YearMonthPickerFragment.newInstance(yearMonth)
        fragment.onValueChanged = onValueSelected
        fragment.show(fragmentManager, fragment.tag)
    }
}