package com.faltenreich.releaseradar.data.repository

import com.faltenreich.releaseradar.data.dao.ReleaseDao
import com.faltenreich.releaseradar.data.enum.MediaType
import com.faltenreich.releaseradar.data.model.Release
import com.faltenreich.releaseradar.data.preference.UserPreferences
import org.threeten.bp.LocalDate

object ReleaseRepository : Repository<Release, ReleaseDao>(ReleaseDao) {

    fun getAll(startAt: LocalDate, greaterThan: Boolean, minPopularity: Float, page: Int, pageSize: Int, onSuccess: (List<Release>) -> Unit, onError: ((Exception?) -> Unit)?) {
        dao.getAll(startAt, greaterThan, minPopularity, page, pageSize, onSuccess, onError)
    }

    fun getBetween(startAt: LocalDate, endAt: LocalDate, mediaType: MediaType, pageSize: Int, onSuccess: (List<Release>) -> Unit, onError: ((Exception?) -> Unit)?) {
        dao.getBetween(startAt, endAt, mediaType, pageSize, onSuccess, onError)
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