package com.faltenreich.release.ui.list.pagination

import androidx.paging.PageKeyedDataSource
import com.faltenreich.release.data.repository.ReleaseRepository
import com.faltenreich.release.data.repository.RepositoryFactory
import com.faltenreich.release.ui.list.item.ReleaseListItem
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

    private fun load(date: LocalDate, minPageSize: Int, descending: Boolean, callback: LoadCallback<ReleaseKey, ReleaseListItem>) {
        loadItemsForDate(date, minPageSize, descending) { items, adjacentDate -> callback.onResult(items, adjacentDate) }
    }

    private fun loadItemsForDate(date: LocalDate, minPageSize: Int, descending: Boolean, givenItems: List<ReleaseListItem> = listOf(), callback: (List<ReleaseListItem>, LocalDate) -> Unit) {
        releaseRepository.getAll(date) { releases ->
            val dayItem = ReleaseListItem(date, null)
            val releaseItems = releases.map { release -> ReleaseListItem(release.releaseDate, release) }
            val newItems = listOf(dayItem).plus(releaseItems)
            val items = if (descending) givenItems.plus(newItems) else newItems.plus(givenItems)

            val adjacentDate = if (descending) date.plusDays(1) else date.minusDays(1)
            if (items.size > minPageSize) {
                callback(items, adjacentDate)
            } else {
                loadItemsForDate(adjacentDate, minPageSize, descending, items, callback)
            }
        }
    }
}