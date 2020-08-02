package com.faltenreich.release.data.repository

import com.faltenreich.release.data.dao.CalendarEventDao
import com.faltenreich.release.data.dao.factory.DaoFactory
import com.faltenreich.release.data.model.CalendarEvent

object CalendarEventRepository : Repository<CalendarEvent>(), CalendarEventDao by DaoFactory.dao(CalendarEventDao::class)