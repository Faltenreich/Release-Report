package com.faltenreich.releaseradar.data.dao

import com.faltenreich.releaseradar.data.enum.MediaType
import com.faltenreich.releaseradar.data.model.Model
import com.faltenreich.releaseradar.data.model.Release
import com.faltenreich.releaseradar.extension.date
import com.faltenreich.releaseradar.parse.database.whereContainsText
import org.threeten.bp.LocalDate

object ReleaseDao : BaseDao<Release>(Release::class) {
    override val modelName: String = "Release"

    fun getAll(startAt: LocalDate, greaterThan: Boolean, minPopularity: Float, page: Int, pageSize: Int, onResult: (List<Release>) -> Unit) {
        getQuery()
            .run {
                if (greaterThan) {
                    whereGreaterThan(Release.RELEASED_AT, startAt.date).orderByAscending(Release.RELEASED_AT)
                } else {
                    whereLessThan(Release.RELEASED_AT, startAt.date).orderByDescending(Release.RELEASED_AT)
                }
            }
            .whereGreaterThan(Release.POPULARITY, minPopularity)
            .setSkip(page * pageSize)
            .setLimit(pageSize)
            .findInBackground { releases -> onResult(releases.sortedBy(Release::releaseDate)) }
    }

    fun getByIds(ids: Collection<String>, type: MediaType?, startAt: LocalDate?, onResult: (List<Release>) -> Unit) {
        getQuery()
            .whereContainedIn(Model.ID, ids)
            .run { type?.key?.let { key -> whereEqualTo(Release.TYPE, key) } ?: this }
            .run { startAt?.date?.let { date -> whereGreaterThan(Release.RELEASED_AT, date).orderByAscending(Release.RELEASED_AT) } ?: this }
            .findInBackground(onResult)
    }

    fun getBetween(startAt: LocalDate, endAt: LocalDate, mediaType: MediaType, pageSize: Int, onResult: (List<Release>) -> Unit) {
        getQuery()
            .whereEqualTo(Release.TYPE, mediaType.key)
            .whereGreaterThan(Release.RELEASED_AT, startAt.date)
            .whereLessThan(Release.RELEASED_AT, endAt.date)
            .orderByDescending(Release.POPULARITY)
            .setLimit(pageSize)
            .findInBackground { releases -> onResult(releases) }
    }

    fun search(string: String, page: Int, pageSize: Int, onResult: (List<Release>) -> Unit) {
        getQuery()
            .whereContainsText(Release.TITLE, string)
            .orderByDescending(Release.POPULARITY)
            .setSkip(page * pageSize)
            .setLimit(pageSize)
            .findInBackground(onResult)
    }
}