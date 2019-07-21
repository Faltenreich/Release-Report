package com.faltenreich.release.ui.logic.opener

import androidx.navigation.NavController
import com.faltenreich.release.NavigationGraphDirections
import com.faltenreich.release.extension.asString
import org.threeten.bp.YearMonth

interface YearMonthPickerOpener {
    fun openYearMonthPicker(navigationController: NavController, yearMonth: YearMonth? = null, onValueSelected: (YearMonth) -> Unit) {
        navigationController.navigate(NavigationGraphDirections.openYearMonthPicker(yearMonth?.asString ?: ""))
    }
}