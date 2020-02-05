package com.faltenreich.release.data.dao.parse

import com.faltenreich.release.base.date.date
import com.faltenreich.release.data.dao.ReleaseDao
import com.faltenreich.release.data.dao.preference.ReleasePreferenceDao
import com.faltenreich.release.data.model.Model
import com.faltenreich.release.data.model.Release
import com.faltenreich.release.framework.parse.database.ParseDao
import com.faltenreich.release.framework.parse.database.whereContainsText
import org.threeten.bp.LocalDate
import kotlin.reflect.KClass

class ReleaseParseDao : ReleaseDao, ParseDao<Release>, ReleasePreferenceDao {

    override val clazz: KClass<Release> = Release::class
    override val modelName: String = "Release"

    override suspend fun getByIds(ids: Collection<String>, startAt: LocalDate?): List<Release> {
        return getQuery()
            .whereContainedIn(Model.ID, ids)
            .run { startAt?.date?.let { date -> whereGreaterThanOrEqualTo(Release.RELEASED_AT, date).orderByAscending(Release.RELEASED_AT) } ?: this }
            .query()
    }

    override suspend fun getBefore(date: LocalDate, page: Int, pageSize: Int): List<Release> {
        return getQuery()
            .whereLessThanOrEqualTo(Release.RELEASED_AT, date.date)
            // Orders are inverted
            .orderByDescending(Release.RELEASED_AT)
            .addAscendingOrder(Release.POPULARITY)
            .setSkip(page * pageSize)
            .setLimit(pageSize)
            .query().reversed()
    }

    override suspend fun getAfter(date: LocalDate, page: Int, pageSize: Int): List<Release> {
        return getQuery()
            .whereGreaterThanOrEqualTo(Release.RELEASED_AT, date.date)
            .orderByAscending(Release.RELEASED_AT)
            .addDescendingOrder(Release.POPULARITY)
            .setSkip(page * pageSize)
            .setLimit(pageSize)
            .query()
    }

    override suspend fun getBetween(startAt: LocalDate, endAt: LocalDate, pageSize: Int?): List<Release> {
        return getQuery()
            .whereGreaterThanOrEqualTo(Release.RELEASED_AT, startAt.date)
            .whereLessThanOrEqualTo(Release.RELEASED_AT, endAt.date)
            .orderByDescending(Release.POPULARITY)
            .run { pageSize?.let { limit -> setLimit(limit) } ?: this }
            .query()
    }

    override suspend fun search(string: String, page: Int, pageSize: Int): List<Release> {
        return getQuery()
            .whereContainsText(Release.TITLE, string)
            .orderByDescending(Release.POPULARITY)
            .setSkip(page * pageSize)
            .setLimit(pageSize)
            .query()
    }
}