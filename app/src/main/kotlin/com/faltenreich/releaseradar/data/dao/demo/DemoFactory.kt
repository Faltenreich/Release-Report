package com.faltenreich.releaseradar.data.dao.demo

import com.faltenreich.releaseradar.data.enum.MediaType
import com.faltenreich.releaseradar.data.model.Release
import org.threeten.bp.LocalDate

object DemoFactory {

    private val releases by lazy {
        val startDate = LocalDate.now().minusWeeks(2)
        (0 until 100).map { index ->
            Release().apply {
                id = index.toString()
                title = "Release $index"
                releaseDate = startDate.plusDays(index.toLong())
                mediaType = if (index % 3 == 0) MediaType.MOVIE else if (index % 2 == 0) MediaType.MUSIC else MediaType.GAME
                popularity = 100f
            }
        }
    }

    fun releases(): List<Release> {
        return releases
    }
}