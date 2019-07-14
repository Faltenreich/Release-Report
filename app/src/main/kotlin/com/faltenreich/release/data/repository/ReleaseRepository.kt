package com.faltenreich.release.data.repository

import com.faltenreich.release.data.dao.ReleaseDao
import com.faltenreich.release.data.enum.ReleaseType
import com.faltenreich.release.data.model.Release
import com.faltenreich.release.data.preference.UserPreferences
import org.threeten.bp.LocalDate

class ReleaseRepository(dao: ReleaseDao) : Repository<Release, ReleaseDao>(dao) {

    fun getBetween(startAt: LocalDate, endAt: LocalDate, releaseType: ReleaseType? = null, pageSize: Int? = null, onResult: (List<Release>) -> Unit) {
        dao.getBetween(startAt, endAt, releaseType, pageSize, onResult)
    }

    fun search(string: String, page: Int, pageSize: Int, onResult: (List<Release>) -> Unit) {
        dao.search(string, page, pageSize, onResult)
    }

    // TODO: Paging
    fun getFavorites(type: ReleaseType? = null, startAt: LocalDate? = null, onResult: (List<Release>) -> Unit) {
        UserPreferences.favoriteReleaseIds.takeIf(Collection<*>::isNotEmpty)?.let { favoriteReleaseIds ->
            dao.getByIds(favoriteReleaseIds, type, startAt, onResult)
        } ?: onResult(listOf())
    }

    fun getFavorites(startAt: LocalDate, endAt: LocalDate, onResult: (List<Release>) -> Unit) {
        UserPreferences.favoriteReleaseIds.takeIf(Collection<*>::isNotEmpty)?.let { favoriteReleaseIds ->
            dao.getByIds(favoriteReleaseIds, startAt, endAt, onResult)
        } ?: onResult(listOf())
    }
}