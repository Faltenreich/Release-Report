package com.faltenreich.release.ui.list.item

import org.threeten.bp.LocalDate

data class CalendarWeekDayListItem(
    override val date: LocalDate
) : CalendarListItem