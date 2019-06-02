package com.faltenreich.release.extension

import org.threeten.bp.LocalDate

data class DateRange(
    override val start: LocalDate,
    override val endInclusive: LocalDate
) : ClosedRange<LocalDate>
