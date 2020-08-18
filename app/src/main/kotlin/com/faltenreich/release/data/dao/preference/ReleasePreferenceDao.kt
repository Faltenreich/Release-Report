package com.faltenreich.release.data.dao.preference

import com.faltenreich.release.base.date.isAfterOrEqual
import com.faltenreich.release.base.date.isBeforeOrEqual
import com.faltenreich.release.base.primitive.isTrue
import com.faltenreich.release.data.dao.ReleaseDao
import com.faltenreich.release.data.model.Release
import com.faltenreich.release.domain.preference.SubscriptionManager
import org.threeten.bp.LocalDate
import kotlin.math.max
import kotlin.math.min

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

    override suspend fun getSubscriptionsBefore(
        date: LocalDate,
        page: Int,
        pageSize: Int
    ): List<Release> {
        val subscriptions = SubscriptionManager.getSubscriptions().filter { release ->
            val releaseDate = release.releaseDate ?: return@filter false
            releaseDate.isBeforeOrEqual(date)
        }.sortedBy(Release::releaseDate)
        val endIndex = subscriptions.size - 1 - page * pageSize
        val startIndex = max(0, endIndex - pageSize + 1)
        return subscriptions.slice(startIndex..endIndex)
    }

    override suspend fun getSubscriptionsAfter(
        date: LocalDate,
        page: Int,
        pageSize: Int
    ): List<Release> {
        val subscriptions = SubscriptionManager.getSubscriptions().filter { release ->
            val releaseDate = release.releaseDate ?: return@filter false
            releaseDate.isAfterOrEqual(date)
        }.sortedBy(Release::releaseDate)
        val startIndex = page * pageSize
        val endIndex = min(subscriptions.size - 1, startIndex + pageSize - 1)
        return subscriptions.slice(IntRange(startIndex, endIndex))
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