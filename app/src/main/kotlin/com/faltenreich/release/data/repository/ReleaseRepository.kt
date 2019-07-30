package com.faltenreich.release.data.repository

import com.faltenreich.release.data.dao.FavoriteDao
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

    // TODO: Proper filter and pagination
    fun getFavorites(startAt: LocalDate? = null, pageSize: Int, onResult: (List<Release>) -> Unit) {
        FavoriteDao.getFavorites().takeIf(Collection<*>::isNotEmpty)?.let { ids ->
            dao.getByIds(ids, startAt) { releases -> onResult(releases.take(pageSize)) }
        } ?: onResult(listOf())
    }
}