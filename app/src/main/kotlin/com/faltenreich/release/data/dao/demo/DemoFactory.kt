package com.faltenreich.release.data.dao.demo

import com.faltenreich.release.data.enum.MediaType
import com.faltenreich.release.data.model.Genre
import com.faltenreich.release.data.model.Image
import com.faltenreich.release.data.model.Platform
import com.faltenreich.release.data.model.Release
import org.threeten.bp.LocalDate

object DemoFactory {
    private const val IMAGE_PROVIDER = "https://picsum.photos"

    private val releases: List<Release> by lazy {
        val startDate = LocalDate.now().minusWeeks(2)
        (0 until 100).map { index ->
            Release().apply {
                id = index.toString()
                title = "Release ${index + 1}"
                releaseDate = startDate.plusDays(index.toLong())
                mediaType = if (index % 3 == 0) MediaType.MOVIE else if (index % 2 == 0) MediaType.MUSIC else MediaType.GAME
                popularity = 100f
                imageUrlForThumbnail = getImageUrl(index, 300 to 400)
                imageUrlForCover = getImageUrl(index, 1080 to 1920)
                imageUrlForWallpaper = getImageUrl(index, 1920 to 1080)
                genres = listOfNotNull(this@DemoFactory.genres.getOrNull(index / 10)?.id)
                platforms = listOfNotNull(this@DemoFactory.platforms.getOrNull(index / 10)?.id)
                images = this@DemoFactory.images.mapNotNull { image -> image.id }
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
                url = getImageUrl(index, 1080 to 1920)
            }
        }
    }

    private fun getImageUrl(index: Int, size: Pair<Int, Int>): String {
        return "$IMAGE_PROVIDER/id/$index/${size.first}/${size.second}"
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