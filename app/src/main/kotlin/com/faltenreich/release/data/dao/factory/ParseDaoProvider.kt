package com.faltenreich.release.data.dao.factory

import com.faltenreich.release.data.dao.parse.CalendarEventParseDao
import com.faltenreich.release.data.dao.parse.GenreParseDao
import com.faltenreich.release.data.dao.parse.PlatformParseDao
import com.faltenreich.release.data.dao.parse.ReleaseParseDao

class ParseDaoProvider : DaoProvider {
    override fun newReleaseDao() = ReleaseParseDao()
    override fun newGenreDao() = GenreParseDao()
    override fun newPlatformDao() = PlatformParseDao()
    override fun newCalendarEventDao() = CalendarEventParseDao()
}