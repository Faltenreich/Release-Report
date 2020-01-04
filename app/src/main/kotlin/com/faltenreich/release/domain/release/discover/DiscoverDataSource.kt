package com.faltenreich.release.domain.release.discover

import androidx.paging.PageKeyedDataSource
import com.faltenreich.release.base.pagination.PaginationInfo
import com.faltenreich.release.data.model.Release
import com.faltenreich.release.data.repository.ReleaseRepository
import com.faltenreich.release.domain.date.DateProvider
import com.faltenreich.release.domain.release.list.ReleaseDateItem
import com.faltenreich.release.domain.release.list.ReleaseItem
import org.threeten.bp.LocalDate

class DiscoverDataSource(private val startAt: LocalDate) : PageKeyedDataSource<PaginationInfo, DateProvider>() {

    override fun loadInitial(params: LoadInitialParams<PaginationInfo>, callback: LoadInitialCallback<PaginationInfo, DateProvider>) {
        load(PaginationInfo(params, true), object : LoadCallback<PaginationInfo, DateProvider>() {
            override fun onResult(
                data: MutableList<DateProvider>,
                adjacentPageKey: PaginationInfo?
            ) {
                if (data.isNotEmpty()) {
                    callback.onResult(data, PaginationInfo(params, false), adjacentPageKey)
                } else {
                    load(PaginationInfo(params, false), object : LoadCallback<PaginationInfo, DateProvider>() {
                        override fun onResult(
                            data: MutableList<DateProvider>,
                            adjacentPageKey: PaginationInfo?
                        ) {
                            callback.onResult(data, PaginationInfo(params, false), adjacentPageKey)
                        }
                    })
                }
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
                val appendDate = when (info.descending) {
                    true -> info.previousDate == null || date != info.previousDate
                    false -> index != 0
                }
                if (appendDate) {
                    items.add(ReleaseDateItem(date))
                }

                val releasesOfDay = group.second
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
            PaginationInfo(
                info.page + 1,
                info.pageSize,
                info.descending,
                previousDate
            )
        }

        info.previousDate?.let { previousDate ->
            val appendMissingDate = info.page == 0 && !info.descending && items.lastOrNull()?.date != previousDate
            if (appendMissingDate) {
                items.add(ReleaseDateItem(previousDate))
            }
        }

        callback.onResult(items, adjacentPageKey)
    }
}