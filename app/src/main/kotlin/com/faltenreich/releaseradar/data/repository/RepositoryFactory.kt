package com.faltenreich.releaseradar.data.repository

object RepositoryFactory {

    fun repositoryForReleases(): ReleaseRepository {
        return ReleaseRepository()
    }
    fun repositoryForGenres(): GenreRepository {
        return GenreRepository()
    }
    fun repositoryForPlatforms(): PlatformRepository {
        return PlatformRepository()
    }
}