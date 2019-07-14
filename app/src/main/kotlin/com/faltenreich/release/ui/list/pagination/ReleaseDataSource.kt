package com.faltenreich.release.ui.list.pagination

import androidx.paging.PageKeyedDataSource
import com.faltenreich.release.data.repository.ReleaseRepository
import com.faltenreich.release.data.repository.RepositoryFactory
import com.faltenreich.release.extension.isTrue
import com.faltenreich.release.ui.list.item.ReleaseDayListItem
import com.faltenreich.release.ui.list.item.ReleaseListItem
import com.faltenreich.release.ui.list.item.ReleaseReleaseListItem
import org.threeten.bp.LocalDate

private typealias ReleaseKey = LocalDate

class ReleaseDataSource(
    private var startAt: LocalDate,
    private val onInitialLoad: ((Int) -> Unit)? = null
) : PageKeyedDataSource<ReleaseKey, ReleaseListItem>() {
    private val releaseRepository = RepositoryFactory.repository<ReleaseRepository>()

    override fun loadInitial(params: LoadInitialParams<ReleaseKey>, callback: LoadInitialCallback<ReleaseKey, ReleaseListItem>) {
        load(startAt, params.requestedLoadSize, true, object : LoadCallback<ReleaseKey, ReleaseListItem>() {
            override fun onResult(data: MutableList<ReleaseListItem>, adjacentPageKey: ReleaseKey?) {
                onInitialLoad?.invoke(data.size)
                callback.onResult(data, startAt.minusDays(1), adjacentPageKey)
            }
        })
    }

    override fun loadBefore(params: LoadParams<ReleaseKey>, callback: LoadCallback<ReleaseKey, ReleaseListItem>) {
        load(params.key, params.requestedLoadSize, false, callback)
    }

    override fun loadAfter(params: LoadParams<ReleaseKey>, callback: LoadCallback<ReleaseKey, ReleaseListItem>) {
        load(params.key, params.requestedLoadSize, true, callback)
    }

    private fun load(date: LocalDate, pageSize: Int, descending: Boolean, callback: LoadCallback<ReleaseKey, ReleaseListItem>) {
        val progression = if (descending) (0L until pageSize) else (-pageSize + 1L .. 0L)
        val dates = progression.map { page -> date.plusDays(page) }
        val (start, end) = dates.first() to dates.last()
        releaseRepository.getBetween(start, end) { releases ->
            val adjacentDate = if (descending) end.plusDays(1) else start.minusDays(1)
            val items = dates.flatMap { date ->
                val dayItem = ReleaseDayListItem(date)
                val releasesOfDay = releases.filter { release -> release.releaseDate?.equals(date).isTrue }
                val releaseItems = releasesOfDay.mapNotNull { release -> release.releaseDate?.let { date -> ReleaseReleaseListItem(date, release) } }
                listOf(dayItem).plus(releaseItems)
            }
            callback.onResult(items, adjacentDate)
        }
    }
}