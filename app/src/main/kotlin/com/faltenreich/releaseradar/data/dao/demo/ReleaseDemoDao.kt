package com.faltenreich.releaseradar.data.dao.demo

import com.faltenreich.releaseradar.data.dao.ReleaseDao
import com.faltenreich.releaseradar.data.enum.MediaType
import com.faltenreich.releaseradar.data.model.Release
import org.threeten.bp.LocalDate

class ReleaseDemoDao : ReleaseDao {

    private val release = Release().apply {
        id = "0"
        title = "Test"
        releaseDate = LocalDate.now()
        imageUrlForThumbnail = "https://www.google.com/images/branding/googlelogo/2x/googlelogo_color_92x30dp.png"
        imageUrlForCover = "https://www.google.com/images/branding/googlelogo/2x/googlelogo_color_92x30dp.png"
        imageUrlForWallpaper = "https://www.google.com/images/branding/googlelogo/2x/googlelogo_color_92x30dp.png"
        mediaType = MediaType.GAME
    }

    override fun getById(id: String, onResult: (Release?) -> Unit) {
        onResult(release)
    }

    override fun getByIds(ids: Collection<String>, onResult: (List<Release>) -> Unit) {
        onResult(listOf(release))
    }

    override fun getAll(startAt: LocalDate, greaterThan: Boolean, minPopularity: Float, page: Int, pageSize: Int, onResult: (List<Release>) -> Unit) {
        onResult(listOf(release))
    }

    override fun getByIds(ids: Collection<String>, type: MediaType?, startAt: LocalDate?, onResult: (List<Release>) -> Unit) {
        onResult(listOf(release))
    }

    override fun getBetween(startAt: LocalDate, endAt: LocalDate, mediaType: MediaType, pageSize: Int, onResult: (List<Release>) -> Unit) {
        onResult(listOf(release))
    }

    override fun search(string: String, page: Int, pageSize: Int, onResult: (List<Release>) -> Unit) {
        onResult(listOf(release))
    }
}