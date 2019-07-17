package com.faltenreich.release.ui.list.item

import org.threeten.bp.LocalDate
import org.threeten.bp.YearMonth

data class CalendarMonthItem(
    override val date: LocalDate,
    override val yearMonth: YearMonth
) : CalendarItem