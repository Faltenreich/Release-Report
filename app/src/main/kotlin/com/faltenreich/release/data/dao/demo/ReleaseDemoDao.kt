package com.faltenreich.release.data.dao.demo

import com.faltenreich.release.base.date.isAfterOrEqual
import com.faltenreich.release.base.date.isBeforeOrEqual
import com.faltenreich.release.base.primitive.isTrue
import com.faltenreich.release.base.primitive.isTrueOrNull
import com.faltenreich.release.data.dao.ReleaseDao
import com.faltenreich.release.data.dao.preference.ReleasePreferenceDao
import com.faltenreich.release.data.model.Release
import org.threeten.bp.LocalDate
import kotlin.math.max
import kotlin.math.min

class ReleaseDemoDao : ReleaseDao, ReleasePreferenceDao {

    private val releases by lazy { DemoFactory.releases }

    override suspend fun getById(id: String): Release? {
        return releases.firstOrNull { release -> release.id == id }
    }

    override suspend fun getByIds(ids: Collection<String>): List<Release> {
        return releases.filter { release -> ids.contains(release.id) }
    }

    override suspend fun getByIds(ids: Collection<String>, startAt: LocalDate?): List<Release> {
        return releases.filter { release ->
            ids.contains(release.id) && startAt?.let { release.releaseDate?.isAfterOrEqual(startAt).isTrue }.isTrueOrNull
        }
    }

    override suspend fun getBefore(date: LocalDate, page: Int, pageSize: Int): List<Release> {
        val filtered = releases.filter { release -> release.releaseDate?.isBeforeOrEqual(date).isTrue }
        val sorted = filtered.sortedWith(compareBy(Release::releaseDate).thenByDescending(Release::popularity))
        val endIndex = sorted.size - 1 - page * pageSize
        val startIndex = max(0, endIndex - pageSize + 1)
        return sorted.slice(startIndex..endIndex)
    }

    override suspend fun getAfter(date: LocalDate, page: Int, pageSize: Int): List<Release> {
        val filtered = releases.filter { release -> release.releaseDate?.isAfterOrEqual(date).isTrue }
        val sorted = filtered.sortedWith(compareBy(Release::releaseDate).thenByDescending(Release::popularity))
        val startIndex = page * pageSize
        val endIndex = min(sorted.size - 1, startIndex + pageSize - 1)
        return sorted.slice(startIndex..endIndex)
    }

    override suspend fun getBetween(startAt: LocalDate, endAt: LocalDate, pageSize: Int): List<Release> {
        val filtered = releases.filter { release ->
            release.releaseDate?.let { date ->
                date.isAfterOrEqual(startAt) && date.isBeforeOrEqual(endAt)
            }.isTrue
        }
        val sorted = filtered.sortedByDescending(Release::popularity)
        return sorted.take(pageSize)
    }

    override suspend fun getPopular(startAt: LocalDate, endAt: LocalDate): List<Release> {
        return releases.filter { release ->
            release.releaseDate?.let { date ->
                date.isAfterOrEqual(startAt) && date.isBeforeOrEqual(endAt)
            }.isTrue
        }.sortedWith(compareBy(Release::releaseDate).thenBy(Release::popularity))
    }

    override suspend fun search(string: String, page: Int, pageSize: Int): List<Release> {
        val filtered = if (page == 0) releases.filter { release ->
            release.artist?.contains(string, ignoreCase = true).isTrue
                    || release.title?.contains(string, ignoreCase = true).isTrue
        } else listOf()
        return filtered.sortedByDescending(Release::popularity)
    }
}