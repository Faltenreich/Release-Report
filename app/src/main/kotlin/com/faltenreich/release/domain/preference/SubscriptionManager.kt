package com.faltenreich.release.domain.preference

import com.faltenreich.release.data.model.Release
import com.faltenreich.release.data.repository.ReleaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

// Caches subscriptions whose ids are stored via shared preferences
object SubscriptionManager {

    private var subscriptions: MutableMap<String, Release> = mutableMapOf()

    fun init() {
        GlobalScope.launch(Dispatchers.IO) {
            val subscribedReleaseIds = UserPreferences.subscribedReleaseIds
            // TODO: Clean subscriptions at some point to prevent exploding data set
            val releases = ReleaseRepository.getByIds(subscribedReleaseIds)
            val releasesById = releases.mapNotNull { release -> release.id?.let { id -> id to release } }
            subscriptions.putAll(releasesById)
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