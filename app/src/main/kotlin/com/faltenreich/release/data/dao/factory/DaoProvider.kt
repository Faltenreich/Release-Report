package com.faltenreich.release.data.dao.factory

import com.faltenreich.release.data.dao.CalendarEventDao
import com.faltenreich.release.data.dao.GenreDao
import com.faltenreich.release.data.dao.PlatformDao
import com.faltenreich.release.data.dao.ReleaseDao

interface DaoProvider {
    fun newReleaseDao(): ReleaseDao
    fun newGenreDao(): GenreDao
    fun newPlatformDao(): PlatformDao
    fun newCalendarEventDao(): CalendarEventDao
}