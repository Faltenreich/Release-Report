package com.faltenreich.release.domain.release.calendar

import org.threeten.bp.LocalDate
import org.threeten.bp.YearMonth

data class CalendarMonthItem(
    override val date: LocalDate,
    override val yearMonth: YearMonth
) : CalendarItem