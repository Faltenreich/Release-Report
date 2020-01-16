package com.faltenreich.release.data.repository

import com.faltenreich.release.data.dao.ReleaseDao
import com.faltenreich.release.data.model.Release
import org.threeten.bp.LocalDate

object ReleaseRepository : Repository<Release, ReleaseDao>(ReleaseDao::class) {

    fun getBefore(date: LocalDate, page: Int, pageSize: Int, onResult: (List<Release>) -> Unit) {
        dao.getBefore(date, page, pageSize, onResult)
    }

    fun getAfter(date: LocalDate, page: Int, pageSize: Int, onResult: (List<Release>) -> Unit) {
        dao.getAfter(date, page, pageSize, onResult)
    }

    fun getBetween(startAt: LocalDate, endAt: LocalDate, pageSize: Int? = null, onResult: (List<Release>) -> Unit) {
        dao.getBetween(startAt, endAt, pageSize, onResult)
    }

    fun search(string: String, page: Int, pageSize: Int, onResult: (List<Release>) -> Unit) {
        dao.search(string, page, pageSize, onResult)
    }

    fun getSubscriptions(startAt: LocalDate, pageSize: Int, onResult: (List<Release>) -> Unit) {
        dao.getSubscriptions(startAt, pageSize, onResult)
    }

    fun getSubscriptions(startAt: LocalDate, endAt: LocalDate, onResult: (List<Release>) -> Unit) {
        dao.getSubscriptions(startAt, endAt, onResult)
    }

    fun getSubscriptions(date: LocalDate, onResult: (List<Release>) -> Unit) {
        dao.getSubscriptions(date, onResult)
    }

    fun isSubscribed(release: Release): Boolean {
        return dao.isSubscribed(release)
    }

    fun subscribe(release: Release) {
        dao.subscribe(release)
    }

    fun unsubscribe(release: Release) {
        dao.unsubscribe(release)
    }
}