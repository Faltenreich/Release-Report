package com.faltenreich.release.data.repository

import com.faltenreich.release.Application
import com.faltenreich.release.data.dao.demo.GenreDemoDao
import com.faltenreich.release.data.dao.demo.MediaDemoDao
import com.faltenreich.release.data.dao.demo.PlatformDemoDao
import com.faltenreich.release.data.dao.demo.ReleaseDemoDao
import com.faltenreich.release.data.dao.parse.GenreParseDao
import com.faltenreich.release.data.dao.parse.MediaParseDao
import com.faltenreich.release.data.dao.parse.PlatformParseDao
import com.faltenreich.release.data.dao.parse.ReleaseParseDao

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

    fun repositoryForImages(): MediaRepository {
        val dao = if (Application.isDemo) MediaDemoDao() else MediaParseDao()
        return MediaRepository(dao)
    }
}