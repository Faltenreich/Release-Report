package com.faltenreich.release.data.dao

import com.faltenreich.release.data.model.Calendar
import org.threeten.bp.LocalDate

interface CalendarDao : Dao<Calendar> {
    suspend fun getBetween(startAt: LocalDate, endAt: LocalDate): List<Calendar>
}