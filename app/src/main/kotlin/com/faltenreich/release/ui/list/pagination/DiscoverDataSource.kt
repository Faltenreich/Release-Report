package com.faltenreich.release.ui.list.pagination

import androidx.paging.PageKeyedDataSource
import com.faltenreich.release.data.repository.ReleaseRepository
import com.faltenreich.release.data.repository.RepositoryFactory
import com.faltenreich.release.extension.isTrue
import com.faltenreich.release.ui.list.item.ReleaseDateItem
import com.faltenreich.release.ui.list.item.ReleaseEmptyItem
import com.faltenreich.release.ui.list.item.ReleaseItem
import com.faltenreich.release.ui.list.item.ReleaseMoreItem
import com.faltenreich.release.ui.logic.provider.DateProvider
import org.threeten.bp.LocalDate

private typealias DiscoverKey = LocalDate

class DiscoverDataSource(
    private var startAt: LocalDate,
    private val onInitialLoad: ((Int) -> Unit)? = null
) : PageKeyedDataSource<DiscoverKey, DateProvider>() {
    private val releaseRepository = RepositoryFactory.repository<ReleaseRepository>()

    override fun loadInitial(params: LoadInitialParams<DiscoverKey>, callback: LoadInitialCallback<DiscoverKey, DateProvider>) {
        load(startAt, params.requestedLoadSize, true, object : LoadCallback<DiscoverKey, DateProvider>() {
            override fun onResult(data: MutableList<DateProvider>, adjacentPageKey: DiscoverKey?) {
                onInitialLoad?.invoke(data.size)
                callback.onResult(data, startAt.minusDays(1), adjacentPageKey)
            }
        })
    }

    override fun loadBefore(params: LoadParams<DiscoverKey>, callback: LoadCallback<DiscoverKey, DateProvider>) {
        load(params.key, params.requestedLoadSize, false, callback)
    }

    override fun loadAfter(params: LoadParams<DiscoverKey>, callback: LoadCallback<DiscoverKey, DateProvider>) {
        load(params.key, params.requestedLoadSize, true, callback)
    }

    private fun load(date: LocalDate, pageSize: Int, descending: Boolean, callback: LoadCallback<DiscoverKey, DateProvider>) {
        val progression = if (descending) (0L until pageSize) else (-pageSize + 1L .. 0L)
        val dates = progression.map { page -> date.plusDays(page) }
        val (start, end) = dates.first() to dates.last()
        releaseRepository.getBetween(start, end) { releases ->
            val adjacentDate = if (descending) end.plusDays(1) else start.minusDays(1)
            val items = dates.flatMap { date ->
                val dayItem = ReleaseDateItem(date)
                val releasesOfDay = releases.filter { release -> release.releaseDate?.equals(date).isTrue }
                if (releasesOfDay.isNotEmpty()) {
                    val splitUpReleases = releasesOfDay.size > MAXIMUM_RELEASES_PER_DAY
                    if (splitUpReleases) {
                        val threshold = MAXIMUM_RELEASES_PER_DAY - 1
                        val releaseItems = releasesOfDay.subList(0, threshold).mapNotNull { release -> release.releaseDate?.let { date -> ReleaseItem(date, release) } }
                        val moreReleases = releasesOfDay.subList(threshold, releasesOfDay.size)
                        val moreItem = ReleaseMoreItem(date, moreReleases)
                        listOf(dayItem).plus(releaseItems).plus(moreItem)
                    } else {
                        val releaseItems = releasesOfDay.mapNotNull { release -> release.releaseDate?.let { date -> ReleaseItem(date, release) } }
                        listOf(dayItem).plus(releaseItems)
                    }
                } else {
                    listOf(dayItem).plus(ReleaseEmptyItem(date))
                }
            }
            callback.onResult(items, adjacentDate)
        }
    }

    companion object {
        private const val MAXIMUM_RELEASES_PER_DAY = 8
    }
}