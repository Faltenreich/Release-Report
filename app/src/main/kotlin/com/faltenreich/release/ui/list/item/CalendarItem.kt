package com.faltenreich.release.ui.list.item

import org.threeten.bp.LocalDate
import org.threeten.bp.YearMonth

interface CalendarItem {
    val date: LocalDate
    val yearMonth: YearMonth // Used for list items whose date must not match the month (e.g. when filling up a month for a full row)

    val isInSameMonth: Boolean
        get() = date.month == yearMonth.month

    override fun equals(other:Any?): Boolean
}