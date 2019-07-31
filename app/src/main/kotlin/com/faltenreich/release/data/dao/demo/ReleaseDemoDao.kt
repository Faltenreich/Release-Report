package com.faltenreich.release.data.dao.demo

import com.faltenreich.release.data.dao.ReleaseDao
import com.faltenreich.release.data.dao.preference.ReleasePreferenceDao
import com.faltenreich.release.data.model.Release
import com.faltenreich.release.extension.isAfterOrEqual
import com.faltenreich.release.extension.isBeforeOrEqual
import com.faltenreich.release.extension.isTrue
import com.faltenreich.release.extension.isTrueOrNull
import org.threeten.bp.LocalDate

class ReleaseDemoDao : ReleaseDao, ReleasePreferenceDao {
    private val releases by lazy { DemoFactory.releases }

    override fun getById(id: String, onResult: (Release?) -> Unit) {
        onResult(releases.firstOrNull { release -> release.id == id })
    }

    override fun getByIds(ids: Collection<String>, onResult: (List<Release>) -> Unit) {
        onResult(releases.filter { release -> ids.contains(release.id) })
    }

    override fun getByIds(ids: Collection<String>, startAt: LocalDate?, onResult: (List<Release>) -> Unit) {
        onResult(releases.filter { release ->
            ids.contains(release.id) && startAt?.let { release.releaseDate?.isAfterOrEqual(startAt).isTrue }.isTrueOrNull
        })
    }

    override fun getBetween(startAt: LocalDate, endAt: LocalDate, pageSize: Int?, onResult: (List<Release>) -> Unit) {
        val filtered = releases.filter { release ->
            release.releaseDate?.let { date ->
                date.isAfterOrEqual(startAt) && date.isBeforeOrEqual(endAt)
            }.isTrue
        }
        val sorted = filtered.sortedByDescending(Release::popularity)
        val paged = pageSize?.let { sorted.take(pageSize) } ?: sorted
        onResult(paged)
    }

    override fun search(string: String, page: Int, pageSize: Int, onResult: (List<Release>) -> Unit) {
        val filtered = if (page == 0) releases.filter { release -> release.title?.contains(string, ignoreCase = true).isTrue } else listOf()
        val sorted = filtered.sortedByDescending(Release::popularity)
        onResult(sorted)
    }
}