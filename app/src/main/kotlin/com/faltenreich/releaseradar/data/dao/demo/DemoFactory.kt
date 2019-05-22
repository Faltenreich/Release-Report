package com.faltenreich.releaseradar.data.dao.demo

import com.faltenreich.releaseradar.data.enum.MediaType
import com.faltenreich.releaseradar.data.model.Genre
import com.faltenreich.releaseradar.data.model.Image
import com.faltenreich.releaseradar.data.model.Platform
import com.faltenreich.releaseradar.data.model.Release
import org.threeten.bp.LocalDate

object DemoFactory {

    private val releases: List<Release> by lazy {
        val startDate = LocalDate.now().minusWeeks(2)
        (0 until 100).map { index ->
            Release().apply {
                id = index.toString()
                title = "Release ${index + 1}"
                releaseDate = startDate.plusDays(index.toLong())
                mediaType = if (index % 3 == 0) MediaType.MOVIE else if (index % 2 == 0) MediaType.MUSIC else MediaType.GAME
                popularity = 100f
                imageUrlForThumbnail = "https://picsum.photos/id/$index/300/400"
                imageUrlForCover = "https://picsum.photos/id/$index/1080/1920"
                imageUrlForWallpaper = "https://picsum.photos/id/$index/1920/1080"
                genres = listOfNotNull(this@DemoFactory.genres.getOrNull(index / 10)?.id)
                platforms = listOfNotNull(this@DemoFactory.platforms.getOrNull(index / 10)?.id)
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

    private val images: List<Image> by lazy {
        (0 until 10).map { index ->
            Image().apply {
                id = index.toString()
                url = "https://picsum.photos/id/$index/1080/1920"
            }
        }
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

    fun images(): List<Image> {
        return images
    }
}