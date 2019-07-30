package com.faltenreich.release.ui.list.pagination

import androidx.paging.PageKeyedDataSource
import com.faltenreich.release.data.model.Release
import com.faltenreich.release.data.repository.ReleaseRepository
import com.faltenreich.release.ui.list.item.ReleaseItem
import com.faltenreich.release.ui.logic.provider.ReleaseProvider

class SearchDataSource(private val query: String?) : PageKeyedDataSource<Int, ReleaseProvider>() {

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, ReleaseProvider>) {
        load(0, params.requestedLoadSize, object : LoadCallback<Int, ReleaseProvider>() {
            override fun onResult(data: MutableList<ReleaseProvider>, adjacentPageKey: Int?) {
                callback.onResult(data, 0, 1)
            }
        })
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, ReleaseProvider>) = Unit

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, ReleaseProvider>) {
        load(params.key, params.requestedLoadSize, callback)
    }

    private fun load(page: Int, pageSize: Int, callback: LoadCallback<Int, ReleaseProvider>) {
        val onResponse = { data: List<Release> ->
            val items = data.mapNotNull { release -> release.releaseDate?.let { date -> ReleaseItem(release, date) } }
            callback.onResult(items, page + 1)
        }
        query?.let { query -> ReleaseRepository.search(query, page, pageSize) { releases -> onResponse(releases) } } ?: onResponse(listOf())
    }
}