package com.faltenreich.release.ui.list.pagination

import androidx.paging.PageKeyedDataSource
import com.faltenreich.release.data.model.Release
import com.faltenreich.release.data.repository.ReleaseRepository
import com.faltenreich.release.ui.list.item.ReleaseDateItem
import com.faltenreich.release.ui.list.item.ReleaseItem
import com.faltenreich.release.ui.logic.provider.DateProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDate

private typealias ReleaseKey = PaginationInfo

class ReleaseListDataSource(private var startAt: LocalDate) : PageKeyedDataSource<ReleaseKey, DateProvider>() {

    override fun loadInitial(params: LoadInitialParams<ReleaseKey>, callback: LoadInitialCallback<ReleaseKey, DateProvider>) {
        val info = PaginationInfo(0, params.requestedLoadSize, true, null)
        load(info, object : LoadCallback<ReleaseKey, DateProvider>() {
            override fun onResult(data: MutableList<DateProvider>, adjacentPageKey: ReleaseKey?) {
                val previousPageKey = PaginationInfo(0, params.requestedLoadSize, false, startAt.minusDays(1))
                callback.onResult(data, previousPageKey, adjacentPageKey)
            }
        })
    }

    override fun loadBefore(params: LoadParams<ReleaseKey>, callback: LoadCallback<ReleaseKey, DateProvider>) {
        load(params.key, callback)
    }

    override fun loadAfter(params: LoadParams<ReleaseKey>, callback: LoadCallback<ReleaseKey, DateProvider>) {
        load(params.key, callback)
    }

    private fun load(info: PaginationInfo, callback: LoadCallback<ReleaseKey, DateProvider>) {
        val onResult = { releases: List<Release> -> onResponse(releases, info, callback) }
        if (info.descending) {
            ReleaseRepository.getAfter(startAt, info.page, info.pageSize, onResult)
        } else {
            ReleaseRepository.getBefore(startAt.minusDays(1), info.page, info.pageSize, onResult)
        }
    }

    private fun onResponse(releases: List<Release>, info: PaginationInfo, callback: LoadCallback<ReleaseKey, DateProvider>) {
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
}