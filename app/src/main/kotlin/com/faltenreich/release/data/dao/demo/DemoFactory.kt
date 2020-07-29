package com.faltenreich.release.data.dao.demo

import com.faltenreich.release.base.date.Now
import com.faltenreich.release.data.enum.ReleaseType
import com.faltenreich.release.data.model.CalendarEvent
import com.faltenreich.release.data.model.Genre
import com.faltenreich.release.data.model.Platform
import com.faltenreich.release.data.model.Release

object DemoFactory {

    private const val VIDEO_PROVIDER = "https://www.youtube.com"
    private const val IMAGE_PROVIDER = "https://picsum.photos"

    val releases: List<Release> by lazy {
        val startDate = Now.localDate().minusWeeks(1)
        (0 until 100).map { index ->
            Release().apply {
                id = index.toString()
                title = "Release ${index + 1}"
                description = "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet."
                artist = "Artist ${index + 1}"
                releaseDate = startDate.plusDays((index / 3).toLong())
                releaseType = if (index % 3 == 0) ReleaseType.MOVIE else if (index % 2 == 0) ReleaseType.MUSIC else ReleaseType.GAME
                popularity = 100f - index
                externalUrl = "https://www.google.de"
                imageUrlForThumbnail = getImageUrl(index, 300 to 400)
                imageUrlForCover = getImageUrl(index, 1080 to 1920)
                imageUrlForWallpaper = getImageUrl(index, 1920 to 1080)
                genres = listOfNotNull(this@DemoFactory.genres.getOrNull(index / 10)?.id)
                platforms = if (releaseType == ReleaseType.GAME) listOfNotNull(this@DemoFactory.platforms.getOrNull(index / 10)?.id) else null
            }
        }
    }

    val calendarEvents: List<CalendarEvent> by lazy {
        releases.map { release ->
            CalendarEvent().apply {
                date = release.releaseDate
                imageUrl = release.imageUrlForThumbnail
            }
        }
    }

    val genres: List<Genre> by lazy {
        (0 until 10).map { index ->
            Genre().apply {
                id = index.toString()
                title = "Genre ${index + 1}"
            }
        }
    }

    val platforms: List<Platform> by lazy {
        (0 until 10).map { index ->
            Platform().apply {
                id = index.toString()
                title = "Platform ${index + 1}"
            }
        }
    }

    private fun getImageUrl(index: Int, size: Pair<Int, Int>): String {
        return "$IMAGE_PROVIDER/id/$index/${size.first}/${size.second}"
    }

    private fun getVideoUrl(id: String): String {
        return "$VIDEO_PROVIDER/watch?v=$id"
    }
}