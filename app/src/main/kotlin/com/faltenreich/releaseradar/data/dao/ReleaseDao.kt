package com.faltenreich.releaseradar.data.dao

import com.faltenreich.releaseradar.data.model.Release
import com.faltenreich.releaseradar.extension.date
import com.faltenreich.releaseradar.parse.database.whereContainsText
import org.threeten.bp.LocalDate

object ReleaseDao : BaseDao<Release>(Release::class) {
    override val entityName: String = "Release"

    fun getAll(startAt: LocalDate?, endAt: LocalDate?, pageSize: Int, onSuccess: (List<Release>) -> Unit, onError: ((Exception?) -> Unit)?) {
        val query = getQuery()
            .run {
                startAt?.date?.let { date ->
                    whereGreaterThan(Release.RELEASED_AT, date).orderByAscending(Release.RELEASED_AT)
                } ?: endAt?.date?.let { date ->
                    whereLessThan(Release.RELEASED_AT, date).orderByDescending(Release.RELEASED_AT)
                } ?: this
            }
            .setLimit(pageSize)
        findInBackground(query, { releases -> onSuccess(releases.sortedBy(Release::releaseDate)) }, onError)
    }

    fun search(string: String, onSuccess: (List<Release>) -> Unit, onError: ((Exception?) -> Unit)?) {
        val query = getQuery()
            .whereContainsText(Release.TITLE, string)
            .orderByAscending(Release.RELEASED_AT)
        findInBackground(query, onSuccess, onError)
    }
}