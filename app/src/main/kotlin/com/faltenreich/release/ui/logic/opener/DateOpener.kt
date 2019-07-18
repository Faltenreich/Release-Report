package com.faltenreich.release.ui.logic.opener

import android.view.View
import androidx.navigation.findNavController
import com.faltenreich.release.NavigationGraphDirections
import com.faltenreich.release.extension.asString
import org.threeten.bp.LocalDate

interface DateOpener {
    fun openDate(date: LocalDate, source: View) {
        source.findNavController().navigate(NavigationGraphDirections.openDate(date.asString))
    }
}