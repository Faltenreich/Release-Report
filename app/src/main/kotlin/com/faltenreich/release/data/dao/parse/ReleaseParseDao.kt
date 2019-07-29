package com.faltenreich.release.data.dao.parse

import com.faltenreich.release.data.dao.ReleaseDao
import com.faltenreich.release.data.model.Model
import com.faltenreich.release.data.model.Release
import com.faltenreich.release.extension.date
import com.faltenreich.release.parse.database.ParseDao
import com.faltenreich.release.parse.database.whereContainsText
import org.threeten.bp.LocalDate
import kotlin.reflect.KClass

class ReleaseParseDao : ReleaseDao, ParseDao<Release> {
    override val clazz: KClass<Release> = Release::class
    override val modelName: String = "Release"

    override fun getByIds(ids: Collection<String>, startAt: LocalDate?, onResult: (List<Release>) -> Unit) {
        getQuery()
            .whereContainedIn(Model.ID, ids)
            .run { startAt?.date?.let { date -> whereGreaterThanOrEqualTo(Release.RELEASED_AT, date).orderByAscending(Release.RELEASED_AT) } ?: this }
            .findInBackground(onResult)
    }

    override fun getBetween(startAt: LocalDate, endAt: LocalDate, pageSize: Int?, onResult: (List<Release>) -> Unit) {
        getQuery()
            .whereGreaterThanOrEqualTo(Release.RELEASED_AT, startAt.date)
            .whereLessThanOrEqualTo(Release.RELEASED_AT, endAt.date)
            .orderByDescending(Release.RELEASED_AT)
            .addDescendingOrder(Release.POPULARITY)
            .run { pageSize?.let { limit -> setLimit(limit) } ?: this }
            .findInBackground { releases -> onResult(releases) }
    }

    override fun search(string: String, page: Int, pageSize: Int, onResult: (List<Release>) -> Unit) {
        getQuery()
            .whereContainsText(Release.TITLE, string)
            .orderByDescending(Release.POPULARITY)
            .setSkip(page * pageSize)
            .setLimit(pageSize)
            .findInBackground(onResult)
    }
}