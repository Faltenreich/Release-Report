package com.faltenreich.releaseradar.data.repository

import com.faltenreich.releaseradar.data.dao.ReleaseDao
import com.faltenreich.releaseradar.data.enum.MediaType
import com.faltenreich.releaseradar.data.model.Release
import com.faltenreich.releaseradar.data.preference.UserPreferences
import com.faltenreich.releaseradar.extension.isTrueOrNull
import org.threeten.bp.LocalDate

object ReleaseRepository : Repository<Release, ReleaseDao>(ReleaseDao) {

    fun getFavorites(mediaType: MediaType, from: LocalDate? = null, onResult: (List<Release>) -> Unit) {
        val favoriteReleases = mutableListOf<Release>()
        val favoriteReleaseIds = UserPreferences.favoriteReleaseIds
        val onNext = { index: Int -> if (index == (favoriteReleaseIds.size - 1)) onResult(favoriteReleases.toList()) }
        favoriteReleaseIds.forEachIndexed { index, id ->
            getById(id, onSuccess = { release ->
                release?.takeIf { release.mediaType == mediaType && from?.minusDays(1)?.isBefore(release.releaseDate).isTrueOrNull }?.let { favoriteReleases.add(release) }
                onNext(index)
            }, onError = {
                onNext(index)
            })
        }
    }
}