package com.faltenreich.releaseradar.ui.view

import android.content.Context
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import com.faltenreich.releaseradar.data.model.Release
import com.faltenreich.releaseradar.extension.millis
import com.github.sundeepk.compactcalendarview.domain.Event
import org.threeten.bp.LocalDate

class CalendarEvent(@ColorInt color: Int, date: LocalDate) : Event(color, date.millis) {

    companion object {
        fun fromRelease(context: Context, release: Release): CalendarEvent? = release.releaseDate?.let { date ->
            val color = ContextCompat.getColor(context, release.mediaType?.colorResId ?: android.R.color.black)
            CalendarEvent(color, date)
        }
    }
}