package com.faltenreich.release.data.dao.demo

import com.faltenreich.release.data.dao.ReleaseDao
import com.faltenreich.release.data.model.Release
import com.faltenreich.release.extension.isAfterOrEqual
import com.faltenreich.release.extension.isBeforeOrEqual
import com.faltenreich.release.extension.isTrue
import com.faltenreich.release.extension.isTrueOrNull
import org.threeten.bp.LocalDate

class ReleaseDemoDao : ReleaseDao {
    private val releases by lazy { DemoFactory.releases() }

    override fun getById(id: String, onResult: (Release?) -> Unit) {
        onResult(releases.firstOrNull { release -> release.id == id })
    }

    override fun getByIds(ids: Collection<String>, onResult: (List<Release>) -> Unit) {
        onResult(releases.filter { release -> ids.contains(release.id) })
    }

    override fun getByIds(
        ids: Collection<String>,
        startAt: LocalDate,
        endAt: LocalDate,
        onResult: (List<Release>) -> Unit
    ) {
        onResult(releases.filter { release ->
            ids.contains(release.id)
                    && release.releaseDate?.isAfterOrEqual(startAt).isTrue
                    && release.releaseDate?.isBeforeOrEqual(endAt.plusDays(1)).isTrue
        })
    }

    override fun getByIds(ids: Collection<String>, startAt: LocalDate?, onResult: (List<Release>) -> Unit) {
        onResult(releases.filter { release ->
            ids.contains(release.id) && startAt?.let { release.releaseDate?.isAfterOrEqual(startAt).isTrue }.isTrueOrNull
        })
    }

    override fun getBetween(startAt: LocalDate, endAt: LocalDate, pageSize: Int?, onResult: (List<Release>) -> Unit) {
        onResult(releases.filter { release ->
            release.releaseDate?.let { date -> date.isAfterOrEqual(startAt) && date.isBeforeOrEqual(endAt) }.isTrue
        }.let { releases ->
            pageSize?.let { releases.take(pageSize) } ?: releases
        })
    }

    override fun search(string: String, page: Int, pageSize: Int, onResult: (List<Release>) -> Unit) {
        onResult(if (page == 0) releases.filter { release -> release.title?.contains(string, ignoreCase = true).isTrue } else listOf())
    }
}