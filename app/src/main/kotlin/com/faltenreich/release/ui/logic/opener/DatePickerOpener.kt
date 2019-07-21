package com.faltenreich.release.ui.logic.opener

import androidx.navigation.NavController
import com.faltenreich.release.NavigationGraphDirections
import com.faltenreich.release.extension.asString
import org.threeten.bp.LocalDate
import org.threeten.bp.YearMonth

interface DatePickerOpener {
    fun openDatePicker(navigationController: NavController, date: LocalDate? = null, onDatePicked: (LocalDate) -> Unit) {
        navigationController.navigate(NavigationGraphDirections.openDatePicker(date?.asString ?: ""))
    }
    fun openDatePicker(navigationController: NavController, yearMonth: YearMonth? = null, onDatePicked: (YearMonth) -> Unit) {
        navigationController.navigate(NavigationGraphDirections.openDatePicker(yearMonth?.asString ?: ""))
    }
}