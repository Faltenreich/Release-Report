package com.faltenreich.release.ui.list.item

import org.threeten.bp.LocalDate
import org.threeten.bp.YearMonth

data class CalendarMonthListItem(
    override val date: LocalDate,
    override val yearMonth: YearMonth
    ) : CalendarListItem