package com.faltenreich.release.data.dao.demo

import com.faltenreich.release.data.dao.ReleaseDao
import com.faltenreich.release.data.enum.ReleaseType
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
        val filtered = releases.filter { release -> ids.contains(release.id) }
        onResult(filtered)
    }

    override fun getByIds(
        ids: Collection<String>,
        startAt: LocalDate,
        endAt: LocalDate,
        onResult: (List<Release>) -> Unit
    ) {
        val filtered = releases.filter { release ->
            ids.contains(release.id)
                    && release.releaseDate?.isAfterOrEqual(startAt).isTrue
                    && release.releaseDate?.isBeforeOrEqual(endAt.plusDays(1)).isTrue
        }
        onResult(filtered)
    }

    override fun getByIds(ids: Collection<String>, type: ReleaseType?, startAt: LocalDate?, onResult: (List<Release>) -> Unit) {
        val filtered = releases.filter { release ->
            ids.contains(release.id)
                    && startAt?.let { release.releaseDate?.isAfterOrEqual(startAt).isTrue }.isTrueOrNull
                    && type?.let { release.releaseType == type }.isTrueOrNull
        }
        onResult(filtered)
    }

    override fun getBetween(startAt: LocalDate, endAt: LocalDate, releaseType: ReleaseType?, pageSize: Int?, onResult: (List<Release>) -> Unit) {
        val filtered = releases.filter { release ->
            release.releaseDate?.let { date ->
                date.isAfterOrEqual(startAt) && date.isBeforeOrEqual(endAt)
            }.isTrue && releaseType?.let { release.releaseType == releaseType }.isTrueOrNull
        }
        onResult(filtered)
    }

    override fun search(string: String, page: Int, pageSize: Int, onResult: (List<Release>) -> Unit) {
        val filtered = {
            releases.filter { release -> release.title?.contains(string).isTrue }
        }
        onResult(if (page == 0) filtered() else listOf())
    }
}