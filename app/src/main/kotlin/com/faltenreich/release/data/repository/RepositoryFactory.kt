package com.faltenreich.release.data.repository

import com.faltenreich.release.data.dao.DaoFactory

object RepositoryFactory {
    inline fun <reified T : Repository<out Any, out Any>> repository(): T {
        return when (T::class) {
            ReleaseRepository::class -> ReleaseRepository(DaoFactory.dao())
            GenreRepository::class -> GenreRepository(DaoFactory.dao())
            PlatformRepository::class -> PlatformRepository(DaoFactory.dao())
            MediaRepository::class -> MediaRepository(DaoFactory.dao())
            else -> throw IllegalArgumentException("Unknown type: ${T::class}")
        } as T
    }
}