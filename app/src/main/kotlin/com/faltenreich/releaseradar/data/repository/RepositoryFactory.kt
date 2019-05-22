package com.faltenreich.releaseradar.data.repository

import com.faltenreich.releaseradar.Application
import com.faltenreich.releaseradar.data.dao.demo.GenreDemoDao
import com.faltenreich.releaseradar.data.dao.demo.ImageDemoDao
import com.faltenreich.releaseradar.data.dao.demo.PlatformDemoDao
import com.faltenreich.releaseradar.data.dao.demo.ReleaseDemoDao
import com.faltenreich.releaseradar.data.dao.parse.GenreParseDao
import com.faltenreich.releaseradar.data.dao.parse.ImageParseDao
import com.faltenreich.releaseradar.data.dao.parse.PlatformParseDao
import com.faltenreich.releaseradar.data.dao.parse.ReleaseParseDao

object RepositoryFactory {

    fun repositoryForReleases(): ReleaseRepository {
        val dao = if (Application.isDemo) ReleaseDemoDao() else ReleaseParseDao()
        return ReleaseRepository(dao)
    }

    fun repositoryForGenres(): GenreRepository {
        val dao = if (Application.isDemo) GenreDemoDao() else GenreParseDao()
        return GenreRepository(dao)
    }

    fun repositoryForPlatforms(): PlatformRepository {
        val dao = if (Application.isDemo) PlatformDemoDao() else PlatformParseDao()
        return PlatformRepository(dao)
    }

    fun repositoryForImages(): ImageRepository {
        val dao = if (Application.isDemo) ImageDemoDao() else ImageParseDao()
        return ImageRepository(dao)
    }
}