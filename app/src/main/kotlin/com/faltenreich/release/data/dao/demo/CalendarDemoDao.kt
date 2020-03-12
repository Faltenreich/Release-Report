package com.faltenreich.release.data.dao.demo

import com.faltenreich.release.base.date.isAfterOrEqual
import com.faltenreich.release.data.dao.CalendarDao
import com.faltenreich.release.data.model.Calendar
import org.threeten.bp.LocalDate

class CalendarDemoDao : CalendarDao {

    private val calendarItems by lazy { DemoFactory.calendarItems }

    override suspend fun getById(id: String): Calendar? {
        return calendarItems.firstOrNull { calendarItem -> calendarItem.id == id }
    }

    override suspend fun getByIds(ids: Collection<String>): List<Calendar> {
        return calendarItems.filter { calendarItem -> ids.contains(calendarItem.id) }
    }

    override suspend fun getBetween(startAt: LocalDate, endAt: LocalDate): List<Calendar> {
        return calendarItems.filter { calendarItem ->
            val date = calendarItem.date ?: return@filter false
            date.isAfterOrEqual(startAt) && date.isBefore(endAt)
        }
    }
}