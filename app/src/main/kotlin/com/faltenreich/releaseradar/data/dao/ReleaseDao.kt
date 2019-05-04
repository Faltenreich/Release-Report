package com.faltenreich.releaseradar.data.dao

import com.faltenreich.releaseradar.data.model.Release
import com.faltenreich.releaseradar.extension.date
import org.threeten.bp.LocalDate

object ReleaseDao : BaseDao<Release>(Release::class) {
    override val entityName: String = "Release"

    fun getAll(startAt: LocalDate?, endAt: LocalDate?, pageSize: Int, onSuccess: (List<Release>) -> Unit, onError: ((Exception?) -> Unit)?) {
        val query = getQuery()
            .orderByDescending(Release.RELEASED_AT)
            .run { startAt?.date?.let { date -> whereGreaterThan(Release.RELEASED_AT, date) } ?: this }
            .run { endAt?.date?.let { date -> whereLessThan(Release.RELEASED_AT, date) } ?: this }
            .setLimit(pageSize)
        findInBackground(query, onSuccess, onError)
    }
}