package com.faltenreich.releaseradar.data.repository

import com.faltenreich.releaseradar.Application
import com.faltenreich.releaseradar.data.dao.demo.ReleaseDemoDao
import com.faltenreich.releaseradar.data.dao.parse.GenreParseDao
import com.faltenreich.releaseradar.data.dao.parse.PlatformParseDao
import com.faltenreich.releaseradar.data.dao.parse.ReleaseParseDao

object RepositoryFactory {

    fun repositoryForReleases(): ReleaseRepository {
        val dao = if (Application.isDemo) ReleaseDemoDao() else ReleaseParseDao()
        return ReleaseRepository(dao)
    }
    fun repositoryForGenres(): GenreRepository {
        return GenreRepository(GenreParseDao())
    }
    fun repositoryForPlatforms(): PlatformRepository {
        return PlatformRepository(PlatformParseDao())
    }
}