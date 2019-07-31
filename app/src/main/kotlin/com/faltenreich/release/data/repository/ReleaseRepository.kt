package com.faltenreich.release.data.repository

import com.faltenreich.release.data.dao.ReleaseDao
import com.faltenreich.release.data.model.Release
import org.threeten.bp.LocalDate

object ReleaseRepository : Repository<Release, ReleaseDao>(ReleaseDao::class) {

    fun getBetween(startAt: LocalDate, endAt: LocalDate, pageSize: Int? = null, onResult: (List<Release>) -> Unit) {
        dao.getBetween(startAt, endAt, pageSize) { releases -> onResult(releases.sortedByDescending(Release::isFavorite)) }
    }

    fun search(string: String, page: Int, pageSize: Int, onResult: (List<Release>) -> Unit) {
        dao.search(string, page, pageSize)  { releases -> onResult(releases.sortedByDescending(Release::isFavorite)) }
    }

    fun getFavorites(startAt: LocalDate, pageSize: Int, onResult: (List<Release>) -> Unit) {
        dao.getFavorites(startAt, pageSize, onResult)
    }

    fun isFavorite(release: Release): Boolean {
        return dao.isFavorite(release)
    }

    fun addFavorite(release: Release) {
        dao.addFavorite(release)
    }

    fun removeFavorite(release: Release) {
        dao.removeFavorite(release)
    }
}