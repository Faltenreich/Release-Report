package com.faltenreich.release.data.dao.parse

import com.faltenreich.release.data.dao.ReleaseDao
import com.faltenreich.release.data.enum.ReleaseType
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

    override fun getAll(date: LocalDate, onResult: (List<Release>) -> Unit) {
        getQuery()
            .whereEqualTo(Release.RELEASED_AT, date.date)
            .findInBackground { releases -> onResult(releases.sortedBy(Release::releaseDate)) }
    }

    override fun getByIds(
        ids: Collection<String>,
        startAt: LocalDate,
        endAt: LocalDate,
        onResult: (List<Release>) -> Unit
    ) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getByIds(ids: Collection<String>, type: ReleaseType?, startAt: LocalDate?, onResult: (List<Release>) -> Unit) {
        getQuery()
            .whereContainedIn(Model.ID, ids)
            .run { type?.key?.let { key -> whereEqualTo(Release.TYPE, key) } ?: this }
            .run { startAt?.date?.let { date -> whereGreaterThanOrEqualTo(Release.RELEASED_AT, date).orderByAscending(Release.RELEASED_AT) } ?: this }
            .findInBackground(onResult)
    }

    override fun getBetween(startAt: LocalDate, endAt: LocalDate, releaseType: ReleaseType, pageSize: Int, onResult: (List<Release>) -> Unit) {
        getQuery()
            .whereEqualTo(Release.TYPE, releaseType.key)
            .whereGreaterThanOrEqualTo(Release.RELEASED_AT, startAt.date)
            .whereLessThanOrEqualTo(Release.RELEASED_AT, endAt.date)
            .orderByDescending(Release.POPULARITY)
            .setLimit(pageSize)
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