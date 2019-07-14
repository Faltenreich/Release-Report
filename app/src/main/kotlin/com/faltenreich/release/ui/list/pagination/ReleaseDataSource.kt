package com.faltenreich.release.ui.list.pagination

import androidx.paging.PageKeyedDataSource
import com.faltenreich.release.data.repository.ReleaseRepository
import com.faltenreich.release.data.repository.RepositoryFactory
import com.faltenreich.release.extension.isTrue
import com.faltenreich.release.ui.list.item.DateItem
import com.faltenreich.release.ui.list.item.ReleaseDateItem
import com.faltenreich.release.ui.list.item.ReleaseItem
import com.faltenreich.release.ui.list.item.ReleaseMoreItem
import org.threeten.bp.LocalDate
import kotlin.math.min

private typealias ReleaseKey = LocalDate

class ReleaseDataSource(
    private var startAt: LocalDate,
    private val onInitialLoad: ((Int) -> Unit)? = null
) : PageKeyedDataSource<ReleaseKey, DateItem>() {
    private val releaseRepository = RepositoryFactory.repository<ReleaseRepository>()

    override fun loadInitial(params: LoadInitialParams<ReleaseKey>, callback: LoadInitialCallback<ReleaseKey, DateItem>) {
        load(startAt, params.requestedLoadSize, true, object : LoadCallback<ReleaseKey, DateItem>() {
            override fun onResult(data: MutableList<DateItem>, adjacentPageKey: ReleaseKey?) {
                onInitialLoad?.invoke(data.size)
                callback.onResult(data, startAt.minusDays(1), adjacentPageKey)
            }
        })
    }

    override fun loadBefore(params: LoadParams<ReleaseKey>, callback: LoadCallback<ReleaseKey, DateItem>) {
        load(params.key, params.requestedLoadSize, false, callback)
    }

    override fun loadAfter(params: LoadParams<ReleaseKey>, callback: LoadCallback<ReleaseKey, DateItem>) {
        load(params.key, params.requestedLoadSize, true, callback)
    }

    private fun load(date: LocalDate, pageSize: Int, descending: Boolean, callback: LoadCallback<ReleaseKey, DateItem>) {
        val progression = if (descending) (0L until pageSize) else (-pageSize + 1L .. 0L)
        val dates = progression.map { page -> date.plusDays(page) }
        val (start, end) = dates.first() to dates.last()
        releaseRepository.getBetween(start, end) { releases ->
            val adjacentDate = if (descending) end.plusDays(1) else start.minusDays(1)
            val items = dates.flatMap { date ->
                val dayItem = ReleaseDateItem(date)
                val releasesOfDay = releases.filter { release -> release.releaseDate?.equals(date).isTrue }
                val releaseItems = releasesOfDay.subList(0, min(releasesOfDay.size, MAXIMUM_RELEASES_PER_DAY)).mapNotNull { release -> release.releaseDate?.let { date -> ReleaseItem(date, release) } }
                val moreCount = releasesOfDay.size - releaseItems.size
                val moreItem = if (moreCount > 0) ReleaseMoreItem(date, moreCount) else null
                listOf(dayItem).plus(releaseItems).plus(moreItem)
            }
            callback.onResult(items, adjacentDate)
        }
    }

    companion object {
        private const val MAXIMUM_RELEASES_PER_DAY = 5
    }
}