package com.faltenreich.release.domain.release.subscription

import com.faltenreich.release.data.model.Release
import com.faltenreich.release.data.repository.ReleaseRepository
import com.faltenreich.release.domain.release.ReleaseDataLoader
import org.threeten.bp.LocalDate

class SubscriptionListDataLoader : ReleaseDataLoader {

    override suspend fun getBefore(date: LocalDate, page: Int, pageSize: Int): List<Release> {
        return ReleaseRepository.getSubscriptionsBefore(date, page, pageSize)
    }

    override suspend fun getAfter(date: LocalDate, page: Int, pageSize: Int): List<Release> {
        return ReleaseRepository.getSubscriptionsAfter(date, page, pageSize)
    }

    override fun appendDateItems(): Boolean {
        return false
    }
}