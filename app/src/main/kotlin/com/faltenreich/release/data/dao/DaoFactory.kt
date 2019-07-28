package com.faltenreich.release.data.dao

import com.faltenreich.release.Application
import com.faltenreich.release.data.dao.demo.GenreDemoDao
import com.faltenreich.release.data.dao.demo.PlatformDemoDao
import com.faltenreich.release.data.dao.demo.ReleaseDemoDao
import com.faltenreich.release.data.dao.parse.GenreParseDao
import com.faltenreich.release.data.dao.parse.PlatformParseDao
import com.faltenreich.release.data.dao.parse.ReleaseParseDao

object DaoFactory {
    inline fun <reified T : Dao<out Any>> dao(): T {
        return when (T::class) {
            ReleaseDao::class -> if (Application.isDemo) ReleaseDemoDao() else ReleaseParseDao()
            GenreDao::class -> if (Application.isDemo) GenreDemoDao() else GenreParseDao()
            PlatformDao::class -> if (Application.isDemo) PlatformDemoDao() else PlatformParseDao()
            else -> throw IllegalArgumentException("Unknown type: ${T::class}")
        } as T
    }
}