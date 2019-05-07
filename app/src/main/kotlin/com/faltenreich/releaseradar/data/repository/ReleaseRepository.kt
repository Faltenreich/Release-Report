package com.faltenreich.releaseradar.data.repository

import com.faltenreich.releaseradar.data.dao.ReleaseDao
import com.faltenreich.releaseradar.data.model.Release
import com.faltenreich.releaseradar.data.preference.UserPreferences
import org.threeten.bp.LocalDate

object ReleaseRepository : Repository<Release, ReleaseDao>(ReleaseDao) {

    fun getAll(startAt: LocalDate, greaterThan: Boolean, minPopularity: Float, page: Int, pageSize: Int, onSuccess: (List<Release>) -> Unit, onError: ((Exception?) -> Unit)?) {
        dao.getAll(startAt, greaterThan, minPopularity, page, pageSize, onSuccess, onError)
    }

    fun search(string: String, page: Int, pageSize: Int, onSuccess: (List<Release>) -> Unit, onError: ((Exception?) -> Unit)?) {
        dao.search(string, page, pageSize, onSuccess, onError)
    }

    fun getFavorites(onResult: (List<Release>) -> Unit) {
        UserPreferences.favoriteReleaseIds.takeIf(Collection<*>::isNotEmpty)?.let { favoriteReleaseIds ->
            getByIds(favoriteReleaseIds, onSuccess = { favorites ->
                onResult(favorites)
            }, onError = {
                onResult(listOf())
            })
        } ?: onResult(listOf())
    }
}