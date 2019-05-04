package com.faltenreich.releaseradar.data.repository

import com.faltenreich.releaseradar.data.dao.ReleaseDao
import com.faltenreich.releaseradar.data.enum.MediaType
import com.faltenreich.releaseradar.data.model.Release
import com.faltenreich.releaseradar.data.preference.UserPreferences
import com.faltenreich.releaseradar.extension.isTrueOrNull
import org.threeten.bp.LocalDate

object ReleaseRepository : Repository<Release, ReleaseDao>(ReleaseDao) {

    fun getAll(startAt: LocalDate?, endAt: LocalDate?, pageSize: Int, onSuccess: (List<Release>) -> Unit, onError: ((Exception?) -> Unit)?)  {
        dao.getAll(startAt, endAt, pageSize, onSuccess, onError)
    }

    fun search(string: String, onSuccess: (List<Release>) -> Unit, onError: ((Exception?) -> Unit)?) {
        dao.search(string, onSuccess, onError)
    }

    fun getFavorites(mediaType: MediaType? = null, from: LocalDate? = null, onResult: (List<Release>) -> Unit) {
        UserPreferences.favoriteReleaseIds.takeIf(Collection<*>::isNotEmpty)?.let { favoriteReleaseIds ->
            val favoriteReleases = mutableListOf<Release>()
            val onNext = { index: Int -> if (index == (favoriteReleaseIds.size - 1)) onResult(favoriteReleases.toList()) }
            favoriteReleaseIds.forEachIndexed { index, id ->
                getById(id, onSuccess = { release ->
                    release?.takeIf { mediaType?.let { release.mediaType == mediaType }.isTrueOrNull && from?.minusDays(1)?.isBefore(release.releaseDate).isTrueOrNull }?.let { favoriteReleases.add(release) }
                    onNext(index)
                }, onError = {
                    onNext(index)
                })
            }
        } ?: onResult(listOf())
    }
}