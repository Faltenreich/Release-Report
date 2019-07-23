package com.faltenreich.release.ui.list.pagination

import androidx.paging.PageKeyedDataSource
import com.faltenreich.release.data.repository.ReleaseRepository
import com.faltenreich.release.data.repository.RepositoryFactory
import com.faltenreich.release.extension.isTrue
import com.faltenreich.release.ui.list.item.ReleaseDateItem
import com.faltenreich.release.ui.list.item.ReleaseItem
import com.faltenreich.release.ui.logic.provider.DateProvider
import org.threeten.bp.LocalDate

private typealias ReleaseKey = LocalDate

class ReleaseListDataSource(private var startAt: LocalDate) : PageKeyedDataSource<ReleaseKey, DateProvider>() {
    private val releaseRepository = RepositoryFactory.repository<ReleaseRepository>()

    override fun loadInitial(params: LoadInitialParams<ReleaseKey>, callback: LoadInitialCallback<ReleaseKey, DateProvider>) {
        load(startAt, params.requestedLoadSize, true, object : LoadCallback<ReleaseKey, DateProvider>() {
            override fun onResult(data: MutableList<DateProvider>, adjacentPageKey: ReleaseKey?) {
                callback.onResult(data, startAt.minusDays(1), adjacentPageKey)
            }
        })
    }

    override fun loadBefore(params: LoadParams<ReleaseKey>, callback: LoadCallback<ReleaseKey, DateProvider>) {
        load(params.key, params.requestedLoadSize, false, callback)
    }

    override fun loadAfter(params: LoadParams<ReleaseKey>, callback: LoadCallback<ReleaseKey, DateProvider>) {
        load(params.key, params.requestedLoadSize, true, callback)
    }

    private fun load(date: LocalDate, pageSize: Int, descending: Boolean, callback: LoadCallback<ReleaseKey, DateProvider>) {
        val progression = if (descending) (0L until pageSize) else (-pageSize + 1L .. 0L)
        val dates = progression.map { page -> date.plusDays(page) }
        val (start, end) = dates.first() to dates.last()
        releaseRepository.getBetween(start, end) { releases ->
            val adjacentDate = if (descending) end.plusDays(1) else start.minusDays(1)
            val items = dates.flatMap { date ->
                val dayItem = ReleaseDateItem(date)
                val releasesOfDay = releases.filter { release -> release.releaseDate?.equals(date).isTrue }
                val releaseItems = releasesOfDay.mapNotNull { release -> release.releaseDate?.let { date -> ReleaseItem(date, release) } }
                listOf(dayItem).plus(releaseItems)
            }
            callback.onResult(items, adjacentDate)
        }
    }
}