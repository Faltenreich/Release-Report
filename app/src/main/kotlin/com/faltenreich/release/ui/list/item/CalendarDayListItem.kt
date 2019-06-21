package com.faltenreich.release.ui.list.item

import com.faltenreich.release.data.model.Release
import org.threeten.bp.LocalDate
import org.threeten.bp.YearMonth

data class CalendarDayListItem(
    override val date: LocalDate,
    override val yearMonth: YearMonth,
    val releases: List<Release>
) : CalendarListItem