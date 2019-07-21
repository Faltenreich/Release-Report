package com.faltenreich.release.ui.logic.opener

import androidx.fragment.app.FragmentManager
import com.faltenreich.release.ui.fragment.YearMonthPickerFragment
import org.threeten.bp.YearMonth

interface YearMonthPickerOpener {
    fun openYearMonthPicker(fragmentManager: FragmentManager, yearMonth: YearMonth? = null, onValueSelected: (YearMonth) -> Unit) {
        val fragment = YearMonthPickerFragment.newInstance(yearMonth)
        fragment.onValueChanged = onValueSelected
        fragment.show(fragmentManager, fragment.tag)
    }
}