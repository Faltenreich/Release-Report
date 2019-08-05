package com.faltenreich.release.ui.list.pagination

import androidx.paging.PageKeyedDataSource
import com.faltenreich.release.data.model.Release
import com.faltenreich.release.data.repository.ReleaseRepository
import com.faltenreich.release.ui.list.item.ReleaseDateItem
import com.faltenreich.release.ui.list.item.ReleaseItem
import com.faltenreich.release.ui.logic.provider.DateProvider
import org.threeten.bp.LocalDate

class DiscoverDataSource(
    private val startAt: LocalDate,
    private val afterInitialLoad: (Int) -> Unit
) : PageKeyedDataSource<PaginationInfo, DateProvider>() {

    override fun loadInitial(params: LoadInitialParams<PaginationInfo>, callback: LoadInitialCallback<PaginationInfo, DateProvider>) {
        val info = PaginationInfo(0, params.requestedLoadSize, true, null)
        load(info, object : LoadCallback<PaginationInfo, DateProvider>() {
            override fun onResult(data: MutableList<DateProvider>, adjacentPageKey: PaginationInfo?) {
                val previousPageKey = PaginationInfo(0, params.requestedLoadSize, false, null)
                callback.onResult(data, previousPageKey, adjacentPageKey)
                afterInitialLoad(data.size)
            }
        })
    }

    override fun loadBefore(params: LoadParams<PaginationInfo>, callback: LoadCallback<PaginationInfo, DateProvider>) {
        load(params.key, callback)
    }

    override fun loadAfter(params: LoadParams<PaginationInfo>, callback: LoadCallback<PaginationInfo, DateProvider>) {
        load(params.key, callback)
    }

    private fun load(info: PaginationInfo, callback: LoadCallback<PaginationInfo, DateProvider>) {
        val onResult = { releases: List<Release> -> onResponse(releases, info, callback) }
        if (info.descending) {
            ReleaseRepository.getAfter(startAt, info.page, info.pageSize, onResult)
        } else {
            ReleaseRepository.getBefore(startAt.minusDays(1), info.page, info.pageSize, onResult)
        }
    }

    private fun onResponse(releases: List<Release>, info: PaginationInfo, callback: LoadCallback<PaginationInfo, DateProvider>) {
        val items = mutableListOf<DateProvider>()
        val releasesByDate = releases.groupBy(Release::releaseDate)

        releasesByDate.toList().forEachIndexed { index, group ->
            group.first?.let { date ->
                val releasesOfDay = group.second

                val appendDate = when (info.descending) {
                    true -> info.previousDate == null || date != info.previousDate
                    false -> index != 0
                }
                if (appendDate) {
                    items.add(ReleaseDateItem(date))
                }

                if (releasesOfDay.isNotEmpty()) {
                    val listItemsOfDay = releasesOfDay.mapNotNull { release ->
                        release.releaseDate?.let { date ->
                            ReleaseItem(release, date)
                        }
                    }
                    items.addAll(listItemsOfDay)
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

        callback.onResult(items, adjacentPageKey)
    }
}