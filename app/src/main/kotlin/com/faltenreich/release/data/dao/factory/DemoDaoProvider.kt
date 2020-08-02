package com.faltenreich.release.data.dao.factory

import com.faltenreich.release.data.dao.demo.CalendarEventDemoDao
import com.faltenreich.release.data.dao.demo.GenreDemoDao
import com.faltenreich.release.data.dao.demo.PlatformDemoDao
import com.faltenreich.release.data.dao.demo.ReleaseDemoDao

class DemoDaoProvider : DaoProvider {
    override fun newReleaseDao() = ReleaseDemoDao()
    override fun newGenreDao() = GenreDemoDao()
    override fun newPlatformDao() = PlatformDemoDao()
    override fun newCalendarEventDao() = CalendarEventDemoDao()
}