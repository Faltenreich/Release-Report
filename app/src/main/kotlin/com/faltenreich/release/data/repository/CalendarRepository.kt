package com.faltenreich.release.data.repository

import com.faltenreich.release.data.dao.CalendarDao
import com.faltenreich.release.data.dao.DaoFactory
import com.faltenreich.release.data.model.Calendar

object CalendarRepository : Repository<Calendar>(), CalendarDao by DaoFactory.dao(CalendarDao::class)