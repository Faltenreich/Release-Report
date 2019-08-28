package com.faltenreich.release.data.dao.preference

import com.faltenreich.release.data.dao.ReleaseDao
import com.faltenreich.release.data.model.Release
import com.faltenreich.release.domain.preference.FavoriteManager
import com.faltenreich.release.base.date.isAfterOrEqual
import com.faltenreich.release.base.primitive.isTrue
import org.threeten.bp.LocalDate

interface ReleasePreferenceDao : ReleaseDao {

    override fun getFavorites(startAt: LocalDate, pageSize: Int, onResult: (List<Release>) -> Unit) {
        onResult(FavoriteManager.getFavorites().filter { release -> release.releaseDate?.isAfterOrEqual(startAt).isTrue }.take(pageSize))
    }

    override fun isFavorite(release: Release): Boolean {
        return FavoriteManager.isFavorite(release)
    }

    override fun addFavorite(release: Release) {
        FavoriteManager.addFavorite(release)
    }

    override fun removeFavorite(release: Release) {
        FavoriteManager.removeFavorite(release)
    }
}