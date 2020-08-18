package com.faltenreich.release.data.dao

import com.faltenreich.release.data.model.Release
import org.threeten.bp.LocalDate

interface ReleaseDao : Dao<Release> {
    suspend fun getByIds(ids: Collection<String>, startAt: LocalDate?): List<Release>
    suspend fun getBefore(date: LocalDate, page: Int, pageSize: Int): List<Release>
    suspend fun getAfter(date: LocalDate, page: Int, pageSize: Int): List<Release>
    suspend fun getBetween(startAt: LocalDate, endAt: LocalDate, pageSize: Int): List<Release>
    suspend fun search(string: String, page: Int, pageSize: Int): List<Release>
    suspend fun getSubscriptions(startAt: LocalDate, pageSize: Int): List<Release>
    suspend fun getSubscriptions(startAt: LocalDate, endAt: LocalDate): List<Release>
    suspend fun getSubscriptions(date: LocalDate): List<Release>
    suspend fun getSubscriptionsBefore(date: LocalDate, page: Int, pageSize: Int): List<Release>
    suspend fun getSubscriptionsAfter(date: LocalDate, page: Int, pageSize: Int): List<Release>
    fun isSubscribed(release: Release): Boolean
    fun subscribe(release: Release)
    fun unsubscribe(release: Release)
}