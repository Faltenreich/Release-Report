package com.faltenreich.release.data.dao.preference

import com.faltenreich.release.base.date.isAfterOrEqual
import com.faltenreich.release.base.date.isBeforeOrEqual
import com.faltenreich.release.base.primitive.isTrue
import com.faltenreich.release.data.dao.ReleaseDao
import com.faltenreich.release.data.model.Release
import com.faltenreich.release.domain.preference.SubscriptionManager
import org.threeten.bp.LocalDate

interface ReleasePreferenceDao : ReleaseDao {

    override suspend fun getSubscriptions(startAt: LocalDate, pageSize: Int): List<Release> {
        return SubscriptionManager.getSubscriptions().filter { release ->
            release.releaseDate?.isAfterOrEqual(startAt).isTrue
        }.sortedBy(Release::releaseDate).take(pageSize)
    }

    override suspend fun getSubscriptions(date: LocalDate): List<Release> {
        return SubscriptionManager.getSubscriptions().filter { release ->
            release.releaseDate?.isEqual(date).isTrue
        }
    }

    override suspend fun getSubscriptions(startAt: LocalDate, endAt: LocalDate): List<Release> {
        return SubscriptionManager.getSubscriptions().filter { release ->
            val date = release.releaseDate ?: return@filter false
            date.isAfterOrEqual(startAt) && date.isBeforeOrEqual(endAt)
        }.sortedBy(Release::releaseDate)
    }

    override fun isSubscribed(release: Release): Boolean {
        return SubscriptionManager.isSubscribed(release)
    }

    override fun subscribe(release: Release) {
        SubscriptionManager.subscribe(release)
    }

    override fun unsubscribe(release: Release) {
        SubscriptionManager.unsubscribe(release)
    }
}