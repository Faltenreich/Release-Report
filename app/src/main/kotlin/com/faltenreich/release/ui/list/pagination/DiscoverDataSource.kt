package com.faltenreich.release.ui.list.pagination

import androidx.paging.PageKeyedDataSource
import com.faltenreich.release.data.model.Release
import com.faltenreich.release.data.repository.ReleaseRepository
import com.faltenreich.release.ui.list.item.ReleaseDateItem
import com.faltenreich.release.ui.list.item.ReleaseItem
import com.faltenreich.release.ui.list.item.ReleaseMoreItem
import com.faltenreich.release.ui.logic.provider.DateProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDate

private typealias DiscoverKey = PaginationInfo

class DiscoverDataSource(
    private var startAt: LocalDate,
    private val afterLoadInitial: (Int) -> Unit
) : PageKeyedDataSource<DiscoverKey, DateProvider>() {

    override fun loadInitial(params: LoadInitialParams<DiscoverKey>, callback: LoadInitialCallback<DiscoverKey, DateProvider>) {
        val info = PaginationInfo(0, params.requestedLoadSize, true, null)
        load(info, object : LoadCallback<DiscoverKey, DateProvider>() {
            override fun onResult(data: MutableList<DateProvider>, adjacentPageKey: DiscoverKey?) {
                val previousPageKey = PaginationInfo(0, params.requestedLoadSize, false, startAt.minusDays(1))
                callback.onResult(data, previousPageKey, adjacentPageKey)
                afterLoadInitial(data.size)
            }
        })
    }

    override fun loadBefore(params: LoadParams<DiscoverKey>, callback: LoadCallback<DiscoverKey, DateProvider>) {
        load(params.key, callback)
    }

    override fun loadAfter(params: LoadParams<DiscoverKey>, callback: LoadCallback<DiscoverKey, DateProvider>) {
        load(params.key, callback)
    }

    private fun load(info: PaginationInfo, callback: LoadCallback<DiscoverKey, DateProvider>) {
        val onResult = { releases: List<Release> -> onResponse(releases, info, callback) }
        if (info.descending) {
            ReleaseRepository.getAfter(startAt, info.page, info.pageSize, onResult)
        } else {
            ReleaseRepository.getBefore(startAt.minusDays(1), info.page, info.pageSize, onResult)
        }
    }

    private fun onResponse(releases: List<Release>, info: PaginationInfo, callback: LoadCallback<DiscoverKey, DateProvider>) {
        GlobalScope.launch {

            val items = mutableListOf<DateProvider>()
            val releasesByDate = releases.groupBy(Release::releaseDate)

            releasesByDate.toList().forEachIndexed { index, group ->
                group.first?.let { date ->
                    val appendDate = when (info.descending) {
                        true -> info.previousDate == null || date != info.previousDate
                        false -> index != 0
                    }
                    if (appendDate) {
                        items.add(ReleaseDateItem(date))
                    }

                    val releasesOfDay = group.second
                    if (releasesOfDay.isNotEmpty()) {
                        val splitUpReleases = releasesOfDay.size > MAXIMUM_RELEASES_PER_DAY
                        if (splitUpReleases) {
                            val threshold = MAXIMUM_RELEASES_PER_DAY - 1
                            val releaseItems = releasesOfDay.subList(0, threshold).mapNotNull { release ->
                                release.releaseDate?.let { date ->
                                    ReleaseItem(
                                        release,
                                        date
                                    )
                                }
                            }
                            items.addAll(releaseItems)
                            val moreReleases = releasesOfDay.subList(threshold, releasesOfDay.size)
                            val moreItem = ReleaseMoreItem(date, moreReleases)
                            items.add(moreItem)

                        } else {
                            val releaseItems = releasesOfDay.mapNotNull { release ->
                                release.releaseDate?.let { date ->
                                    ReleaseItem(
                                        release,
                                        date
                                    )
                                }
                            }
                            items.addAll(releaseItems)
                        }
                    }
                }
            }

            val adjacentPageKey = items.takeIf(List<*>::isNotEmpty)?.let {
                val previousDate = if (info.descending) items.last().date else items.first().date
                PaginationInfo(info.page + 1, info.pageSize, info.descending, previousDate)
            }

            info.previousDate?.let { previousDate ->
                val appendMissingDate = !info.descending && items.lastOrNull()?.date != previousDate
                if (appendMissingDate) {
                    items.add(ReleaseDateItem(previousDate))
                }
            }

            GlobalScope.launch(Dispatchers.Main) { callback.onResult(items, adjacentPageKey) }
        }
    }

    companion object {
        private const val MAXIMUM_RELEASES_PER_DAY = 8
    }
}