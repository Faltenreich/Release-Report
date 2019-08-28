package com.faltenreich.release.domain.release.calendar

import com.faltenreich.release.data.model.Release
import org.threeten.bp.LocalDate
import org.threeten.bp.YearMonth

data class CalendarDayItem(
    override val date: LocalDate,
    override val yearMonth: YearMonth,
    val releases: List<Release>
) : CalendarItem