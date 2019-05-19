package com.faltenreich.releaseradar.data.dao.demo

import com.faltenreich.releaseradar.data.enum.MediaType
import com.faltenreich.releaseradar.data.model.Release
import org.threeten.bp.LocalDate

object DemoFactory {
    private const val IMAGE_URL_THUMB = "https://images.pexels.com/photos/1112048/pexels-photo-1112048.jpeg?auto=compress&cs=tinysrgb&dpr=2&w=400"
    private const val IMAGE_URL = "https://images.pexels.com/photos/1112048/pexels-photo-1112048.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260"

    private val releases by lazy {
        val startDate = LocalDate.now().minusWeeks(2)
        (0 until 100).map { index ->
            Release().apply {
                id = index.toString()
                title = "Release $index"
                releaseDate = startDate.plusDays(index.toLong())
                imageUrlForThumbnail = IMAGE_URL_THUMB
                imageUrlForCover = IMAGE_URL
                imageUrlForWallpaper = IMAGE_URL
                mediaType = MediaType.MOVIE
                popularity = 100f
            }
        }
    }

    fun releases(): List<Release> {
        return releases
    }
}