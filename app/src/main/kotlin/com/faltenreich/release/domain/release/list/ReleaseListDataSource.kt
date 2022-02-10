package com.faltenreich.release.domain.release.list

import androidx.paging.PageKeyedDataSource
import com.faltenreich.release.base.pagination.PaginationInfo
import com.faltenreich.release.data.model.Release
import com.faltenreich.release.data.repository.ReleaseRepository
import com.faltenreich.release.domain.date.DateProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDate

class ReleaseListDataSource(
    private val scope: CoroutineScope,
    private val startAt: LocalDate
) : PageKeyedDataSource<PaginationInfo, DateProvider>() {

    @Suppress("NAME_SHADOWING")
    override fun loadInitial(
        params: LoadInitialParams<PaginationInfo>,
        callback: LoadInitialCallback<PaginationInfo, DateProvider>
    ) {
        loadInitial(params, true) { data, adjacentPageKey ->
            if (data.isNotEmpty()) {
                val previousPageKey = PaginationInfo(0, params.requestedLoadSize, false, null)
                callback.onResult(data, previousPageKey, adjacentPageKey)
            } else {
                loadInitial(params, false) { data, adjacentPageKey ->
                    val previousPageKey = PaginationInfo(1, params.requestedLoadSize, false, adjacentPageKey?.previousDate)
                    callback.onResult(data, previousPageKey, null)
                }
            }
        }
    }

    private fun loadInitial(
        params: LoadInitialParams<PaginationInfo>,
        descending: Boolean,
        onResponse: (List<DateProvider>, PaginationInfo?) -> Unit
    ) {
        val info = PaginationInfo(0, params.requestedLoadSize, descending, null)
        load(info, object : LoadCallback<PaginationInfo, DateProvider>() {
            override fun onResult(data: List<DateProvider>, adjacentPageKey: PaginationInfo?) {
                onResponse(data, adjacentPageKey)
            }
        })
    }

    override fun loadBefore(
        params: LoadParams<PaginationInfo>,
        callback: LoadCallback<PaginationInfo, DateProvider>
    ) {
        load(params.key, callback)
    }

    override fun loadAfter(
        params: LoadParams<PaginationInfo>,
        callback: LoadCallback<PaginationInfo, DateProvider>
    ) {
        load(params.key, callback)
    }

    private fun load(
        info: PaginationInfo,
        callback: LoadCallback<PaginationInfo, DateProvider>
    ) {
        scope.launch(Dispatchers.IO) {
            val releases =
                if (info.descending) ReleaseRepository.getAfter(startAt, info.page, info.pageSize)
                else ReleaseRepository.getBefore(startAt.minusDays(1), info.page, info.pageSize)
            scope.launch(Dispatchers.Main) {
                onResponse(releases, info, callback)
            }
        }
    }

    private fun onResponse(
        releases: List<Release>,
        info: PaginationInfo,
        callback: LoadCallback<PaginationInfo, DateProvider>
    ) {
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

        if (items.size < info.pageSize) {
            if (!info.descending && info.previousDate != null) {
                items.add(ReleaseDateItem(info.previousDate))
            }
            callback.onResult(items, null)
        } else {
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
                val appendMissingDate = info.page == 0
                        && !info.descending
                        && items.lastOrNull()?.date != previousDate
                if (appendMissingDate) {
                    items.add(ReleaseDateItem(previousDate))
                }
            }

            callback.onResult(items, adjacentPageKey)
        }
    }
}