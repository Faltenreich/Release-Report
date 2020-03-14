package com.faltenreich.release.data.dao

import com.faltenreich.release.data.model.CalendarEvent
import org.threeten.bp.LocalDate

interface CalendarEventDao : Dao<CalendarEvent> {
    suspend fun getBetween(startAt: LocalDate, endAt: LocalDate): List<CalendarEvent>
}