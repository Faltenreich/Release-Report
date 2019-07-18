package com.faltenreich.release.ui.logic.opener

import androidx.navigation.NavController
import com.faltenreich.release.NavigationGraphDirections
import com.faltenreich.release.extension.asString
import org.threeten.bp.LocalDate

interface DateOpener {
    fun openDate(navigationController: NavController, date: LocalDate) {
        navigationController.navigate(NavigationGraphDirections.openDate(date.asString))
    }
}