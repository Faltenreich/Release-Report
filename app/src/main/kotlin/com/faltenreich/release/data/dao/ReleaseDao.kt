package com.faltenreich.release.data.dao

import com.faltenreich.release.data.model.Release
import org.threeten.bp.LocalDate

interface ReleaseDao : Dao<Release> {
    fun getByIds(ids: Collection<String>, startAt: LocalDate?, onResult: (List<Release>) -> Unit)
    fun getBefore(date: LocalDate, page: Int, pageSize: Int, onResult: (List<Release>) -> Unit)
    fun getAfter(date: LocalDate, page: Int, pageSize: Int, onResult: (List<Release>) -> Unit)
    fun getBetween(startAt: LocalDate, endAt: LocalDate, pageSize: Int? = null, onResult: (List<Release>) -> Unit)
    fun search(string: String, page: Int, pageSize: Int, onResult: (List<Release>) -> Unit)
    fun getSubscriptions(startAt: LocalDate, pageSize: Int, onResult: (List<Release>) -> Unit)
    fun getSubscriptions(date: LocalDate, onResult: (List<Release>) -> Unit)
    fun isSubscribed(release: Release): Boolean
    fun subscribe(release: Release)
    fun unsubscribe(release: Release)
}