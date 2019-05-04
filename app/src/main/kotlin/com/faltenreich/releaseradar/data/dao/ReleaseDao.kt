package com.faltenreich.releaseradar.data.dao

import com.faltenreich.releaseradar.data.model.Release
import com.faltenreich.releaseradar.extension.date
import com.faltenreich.releaseradar.parse.database.whereContainsText
import org.threeten.bp.LocalDate

object ReleaseDao : BaseDao<Release>(Release::class) {
    override val entityName: String = "Release"

    fun getAll(startAt: LocalDate, greaterThan: Boolean, page: Int, pageSize: Int, onSuccess: (List<Release>) -> Unit, onError: ((Exception?) -> Unit)?) {
        val date = startAt.date
        val query = getQuery()
            .run {
                if (greaterThan) {
                    whereGreaterThan(Release.RELEASED_AT, date).orderByAscending(Release.RELEASED_AT)
                } else {
                    whereLessThan(Release.RELEASED_AT, date).orderByDescending(Release.RELEASED_AT)
                }
            }
            .setSkip(page * pageSize)
            .setLimit(pageSize)
        findInBackground(query, { releases -> onSuccess(releases.sortedBy(Release::releaseDate)) }, onError)
    }

    fun search(string: String, page: Int, pageSize: Int, onSuccess: (List<Release>) -> Unit, onError: ((Exception?) -> Unit)?) {
        val query = getQuery()
            .whereContainsText(Release.TITLE, string)
            .orderByDescending(Release.POPULARTIY)
            .setSkip(page * pageSize)
            .setLimit(pageSize)
        findInBackground(query, onSuccess, onError)
    }
}