package com.faltenreich.release.base.date

import org.threeten.bp.LocalDate

class LocalDateProgression(
    override val start: LocalDate,
    override val endInclusive: LocalDate,
    private val stepDays: Long = 1
) : Iterable<LocalDate>, ClosedRange<LocalDate> {
    override fun iterator(): Iterator<LocalDate> =
        LocalDateIterator(start, endInclusive, stepDays)
}