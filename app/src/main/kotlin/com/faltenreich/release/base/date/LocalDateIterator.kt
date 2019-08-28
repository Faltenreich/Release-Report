package com.faltenreich.release.base.date

import org.threeten.bp.LocalDate

class LocalDateIterator(
    startDate: LocalDate,
    private val endDateInclusive: LocalDate,
    private val stepDays: Long
): Iterator<LocalDate> {
    private var currentDate = startDate

    override fun hasNext() = currentDate <= endDateInclusive

    override fun next(): LocalDate {
        val next = currentDate
        currentDate = currentDate.plusDays(stepDays)
        return next
    }
}