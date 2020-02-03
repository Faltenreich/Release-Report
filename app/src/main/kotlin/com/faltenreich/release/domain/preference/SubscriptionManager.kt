package com.faltenreich.release.domain.preference

import com.faltenreich.release.data.model.Release
import com.faltenreich.release.data.repository.ReleaseRepository

// Caches subscriptions whose ids are stored via shared preferences
object SubscriptionManager {

    private var subscriptions: MutableMap<String, Release> = mutableMapOf()

    fun init() {
        val subscribedReleaseIds = UserPreferences.subscribedReleaseIds
        // TODO: Clean subscriptions at some point to prevent exploding data set
        ReleaseRepository.getByIds(subscribedReleaseIds) { releases ->
            val subscriptions = releases.mapNotNull { release -> release.id?.let { id -> id to release } }
            this.subscriptions.putAll(subscriptions)
        }
    }

    private fun invalidate() {
        UserPreferences.subscribedReleaseIds = subscriptions.keys
    }

    // FIXME: May be empty due to init() not being processed yet - make asynchronous
    fun getSubscriptions(): Collection<Release> {
        return subscriptions.values
    }

    fun isSubscribed(release: Release): Boolean {
        return subscriptions[release.id] != null
    }

    fun subscribe(release: Release) {
        val id = release.id ?: return
        subscriptions[id] = release
        invalidate()
    }

    fun unsubscribe(release: Release) {
        subscriptions.remove(release.id)
        invalidate()
    }
}