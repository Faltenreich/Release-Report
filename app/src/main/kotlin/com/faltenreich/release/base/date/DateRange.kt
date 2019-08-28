package com.faltenreich.release.base.date

import org.threeten.bp.LocalDate

data class DateRange(
    override val start: LocalDate,
    override val endInclusive: LocalDate
) : ClosedRange<LocalDate>
