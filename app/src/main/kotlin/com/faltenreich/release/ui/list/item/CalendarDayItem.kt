package com.faltenreich.release.ui.list.item

import com.faltenreich.release.data.model.Release
import org.threeten.bp.LocalDate
import org.threeten.bp.YearMonth

data class CalendarDayItem(
    override val date: LocalDate,
    override val yearMonth: YearMonth,
    val hasFavorite: Boolean,
    val releaseCount: Int
) : CalendarItem