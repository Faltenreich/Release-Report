package com.faltenreich.release.ui.list.item

import org.threeten.bp.LocalDate

data class CalendarMonthListItem(
    override val date: LocalDate
) : CalendarListItem