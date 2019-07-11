package com.faltenreich.release.ui.view

import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.navigation.findNavController
import com.faltenreich.release.R
import com.faltenreich.release.extension.asString
import org.threeten.bp.LocalDate

interface DateOpener {
    fun openDate(context: Context, date: LocalDate) {
        (context as? Activity)?.findNavController(R.id.mainNavigationHost)?.navigate(
            R.id.open_date,
            Bundle().apply { putString("date", date.asString) },
            null
        )
    }
}