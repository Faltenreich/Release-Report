package com.faltenreich.release.data.dao.demo

import com.faltenreich.release.data.enum.MediaType
import com.faltenreich.release.data.enum.ReleaseType
import com.faltenreich.release.data.model.Genre
import com.faltenreich.release.data.model.Media
import com.faltenreich.release.data.model.Platform
import com.faltenreich.release.data.model.Release
import org.threeten.bp.LocalDate

object DemoFactory {
    private const val VIDEO_PROVIDER = "https://www.youtube.com"
    private const val IMAGE_PROVIDER = "https://picsum.photos"

    private val releases: List<Release> by lazy {
        val startDate = LocalDate.now().minusDays(1)
        (0 until 100).map { index ->
            Release().apply {
                id = index.toString()
                title = "Release ${index + 1}"
                releaseDate = startDate.plusDays(index.toLong() / 7)
                releaseType = if (index % 3 == 0) ReleaseType.MOVIE else if (index % 2 == 0) ReleaseType.MUSIC else ReleaseType.GAME
                popularity = 100f
                externalUrl = "https://www.google.de"
                imageUrlForThumbnail = getImageUrl(index, 300 to 400)
                imageUrlForCover = getImageUrl(index, 1080 to 1920)
                imageUrlForWallpaper = getImageUrl(index, 1920 to 1080)
                genres = listOfNotNull(this@DemoFactory.genres.getOrNull(index / 10)?.id)
                platforms = if (releaseType == ReleaseType.GAME) listOfNotNull(this@DemoFactory.platforms.getOrNull(index / 10)?.id) else null
                media = this@DemoFactory.media.mapNotNull { media -> media.id }
            }
        }
    }

    private val genres: List<Genre> by lazy {
        (0 until 10).map { index ->
            Genre().apply {
                id = index.toString()
                title = "Genre ${index + 1}"
            }
        }
    }

    private val platforms: List<Platform> by lazy {
        (0 until 10).map { index ->
            Platform().apply {
                id = index.toString()
                title = "Platform ${index + 1}"
            }
        }
    }

    private val media: List<Media> by lazy {
        (0 until 10).map { index ->
            Media().apply {
                id = index.toString()
                mediaType = MediaType.IMAGE
                url = getImageUrl(index, 1080 to 1920)
            }
        }.plus(
            Media().apply {
                id = 10.toString()
                mediaType = MediaType.VIDEO
                url = getVideoUrl("Bey4XXJAqS8")
            }
        )
    }

    private fun getImageUrl(index: Int, size: Pair<Int, Int>): String {
        return "$IMAGE_PROVIDER/id/$index/${size.first}/${size.second}"
    }

    private fun getVideoUrl(id: String): String {
        return "$VIDEO_PROVIDER/watch?v=$id"
    }

    fun releases(): List<Release> {
        return releases
    }

    fun genres(): List<Genre> {
        return genres
    }

    fun platforms(): List<Platform> {
        return platforms
    }

    fun media(): List<Media> {
        return media
    }
}