package com.faltenreich.release.ui.list.item

import com.faltenreich.release.data.model.Release
import org.threeten.bp.LocalDate

data class CalendarDayListItem(
    override val date: LocalDate,
    val releases: List<Release>,
    val isInSameMonth: Boolean
) : CalendarListItem