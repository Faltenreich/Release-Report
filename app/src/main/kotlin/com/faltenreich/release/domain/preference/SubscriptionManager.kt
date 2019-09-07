package com.faltenreich.release.domain.preference

import com.faltenreich.release.base.primitive.isTrue
import com.faltenreich.release.data.model.Release
import com.faltenreich.release.data.repository.ReleaseRepository

// Caches subscriptions whose ids are stored via shared preferences
object SubscriptionManager {

    private var subscriptions: MutableSet<Release> = mutableSetOf()

    fun init() {
        val subscribedReleaseIds = UserPreferences.subscribedReleaseIds
        // TODO: Clean subscriptions at some point to prevent exploding data set
        ReleaseRepository.getByIds(subscribedReleaseIds) { releases ->
            subscriptions.addAll(releases)
        }
    }

    private fun invalidate() {
        UserPreferences.subscribedReleaseIds = subscriptions.mapNotNull(Release::id).toSet()
    }

    // FIXME: May be empty due to init() not being processed yet - make asynchronous
    fun getSubscriptions(): List<Release> {
        return subscriptions.toList()
    }

    fun isSubscribed(release: Release): Boolean {
        return subscriptions.contains(release).isTrue
    }

    fun subscribe(release: Release) {
        subscriptions.add(release)
        invalidate()
    }

    fun unsubscribe(release: Release) {
        subscriptions.remove(release)
        invalidate()
    }
}