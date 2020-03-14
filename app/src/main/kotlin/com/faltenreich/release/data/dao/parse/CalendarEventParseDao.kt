package com.faltenreich.release.data.dao.parse

import com.faltenreich.release.base.date.date
import com.faltenreich.release.data.dao.CalendarEventDao
import com.faltenreich.release.data.model.CalendarEvent
import com.faltenreich.release.framework.parse.database.ParseDao
import org.threeten.bp.LocalDate
import kotlin.reflect.KClass

class CalendarEventParseDao : CalendarEventDao, ParseDao<CalendarEvent> {

    override val clazz: KClass<CalendarEvent> = CalendarEvent::class
    override val modelName: String = "CalendarEvent"

    override suspend fun getBetween(startAt: LocalDate, endAt: LocalDate): List<CalendarEvent> {
        return getQuery()
            .whereGreaterThanOrEqualTo(CalendarEvent.DATE, startAt.date)
            .whereLessThanOrEqualTo(CalendarEvent.DATE, endAt.date)
            .orderByAscending(CalendarEvent.DATE)
            .query()
    }
}