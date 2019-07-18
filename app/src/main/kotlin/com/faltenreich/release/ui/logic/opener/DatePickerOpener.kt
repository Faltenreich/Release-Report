package com.faltenreich.release.ui.logic.opener

import androidx.navigation.NavController
import com.faltenreich.release.NavigationGraphDirections
import com.faltenreich.release.extension.asString
import org.threeten.bp.LocalDate

interface DatePickerOpener {
    fun openDatePicker(navigationController: NavController, date: LocalDate? = null) {
        navigationController.navigate(NavigationGraphDirections.openDatePicker(date?.asString ?: ""))
    }
}