package com.faltenreich.release.ui.list.item

import com.faltenreich.release.ui.list.provider.DateProvider
import org.threeten.bp.YearMonth

interface CalendarItem : DateProvider {
    // Used for list items whose date must not match the month (e.g. when filling up a month for a full row)
    val yearMonth: YearMonth
    val isInSameMonth: Boolean
        get() = date.month == yearMonth.month
}