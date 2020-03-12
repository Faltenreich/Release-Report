package com.faltenreich.release.data.dao.parse

import com.faltenreich.release.base.date.date
import com.faltenreich.release.data.dao.CalendarDao
import com.faltenreich.release.data.model.Calendar
import com.faltenreich.release.framework.parse.database.ParseDao
import org.threeten.bp.LocalDate
import kotlin.reflect.KClass

class CalendarParseDao : CalendarDao, ParseDao<Calendar> {

    override val clazz: KClass<Calendar> = Calendar::class
    override val modelName: String = "Calendar"

    override suspend fun getBetween(startAt: LocalDate, endAt: LocalDate): List<Calendar> {
        return getQuery()
            .whereGreaterThanOrEqualTo(Calendar.DATE, startAt.date)
            .whereLessThanOrEqualTo(Calendar.DATE, endAt.date)
            .orderByAscending(Calendar.DATE)
            .query()
    }
}