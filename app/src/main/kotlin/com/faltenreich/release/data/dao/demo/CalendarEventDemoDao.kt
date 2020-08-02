package com.faltenreich.release.data.dao.demo

import com.faltenreich.release.base.date.isAfterOrEqual
import com.faltenreich.release.data.dao.CalendarEventDao
import com.faltenreich.release.data.model.CalendarEvent
import org.threeten.bp.LocalDate

class CalendarEventDemoDao : CalendarEventDao {

    private val calendarItems by lazy { DemoFactory.calendarEvents }

    override suspend fun getById(id: String): CalendarEvent? {
        return calendarItems.firstOrNull { calendarItem -> calendarItem.id == id }
    }

    override suspend fun getByIds(ids: Collection<String>): List<CalendarEvent> {
        return calendarItems.filter { calendarItem -> ids.contains(calendarItem.id) }
    }

    override suspend fun getBetween(startAt: LocalDate, endAt: LocalDate): List<CalendarEvent> {
        return calendarItems.filter { calendarItem ->
            val date = calendarItem.date ?: return@filter false
            date.isAfterOrEqual(startAt) && date.isBefore(endAt)
        }
    }
}