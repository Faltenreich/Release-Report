package com.faltenreich.release.data.dao.factory

import com.faltenreich.release.Application
import com.faltenreich.release.data.dao.*
import kotlin.reflect.KClass

object DaoFactory {

    var provider: DaoProvider = if (Application.isDemo) DemoDaoProvider() else ParseDaoProvider()

    @Suppress("UNCHECKED_CAST")
    fun <T : Dao<out Any>> dao(clazz: KClass<T>): T {
        return when (clazz) {
            ReleaseDao::class -> provider.newReleaseDao()
            GenreDao::class -> provider.newGenreDao()
            PlatformDao::class -> provider.newPlatformDao()
            CalendarEventDao::class -> provider.newCalendarEventDao()
            else -> throw IllegalArgumentException("Unknown type: $clazz")
        } as T
    }
}