package com.faltenreich.release.ui.logic.opener

import androidx.navigation.NavController
import com.faltenreich.release.MainNavigationDirections
import com.faltenreich.release.extension.asString
import org.threeten.bp.LocalDate

interface DateOpener {
    fun openDate(navigationController: NavController, date: LocalDate) {
        navigationController.navigate(MainNavigationDirections.openDate(date.asString))
    }
}