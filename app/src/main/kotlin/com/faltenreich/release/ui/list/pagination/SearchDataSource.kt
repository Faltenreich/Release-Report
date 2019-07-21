package com.faltenreich.release.ui.list.pagination

import androidx.paging.PageKeyedDataSource
import com.faltenreich.release.data.model.Release
import com.faltenreich.release.data.repository.ReleaseRepository
import com.faltenreich.release.data.repository.RepositoryFactory
import com.faltenreich.release.ui.list.item.ReleaseItem
import com.faltenreich.release.ui.logic.provider.ReleaseProvider

class SearchDataSource(
    private val query: String?,
    private val onInitialLoad: ((List<ReleaseProvider>) -> Unit)? = null
) : PageKeyedDataSource<Int, ReleaseProvider>() {
    private val releaseRepository = RepositoryFactory.repository<ReleaseRepository>()

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, ReleaseProvider>) {
        load(0, params.requestedLoadSize, object : LoadCallback<Int, ReleaseProvider>() {
            override fun onResult(data: MutableList<ReleaseProvider>, adjacentPageKey: Int?) {
                onInitialLoad?.invoke(data)
                callback.onResult(data, 0, 1)
            }
        }, onInitialLoad)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, ReleaseProvider>) = Unit

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, ReleaseProvider>) {
        load(params.key, params.requestedLoadSize, callback)
    }

    private fun load(page: Int, pageSize: Int, callback: LoadCallback<Int, ReleaseProvider>, onLoad: ((List<ReleaseProvider>) -> Unit)? = null) {
        val onResponse = { data: List<Release> ->
            val items = data.map { release -> ReleaseItem(release.releaseDate!!, release) }
            onLoad?.invoke(items)
            callback.onResult(items, page + 1)
        }
        query?.let { query -> releaseRepository.search(query, page, pageSize) { releases -> onResponse(releases) } } ?: onResponse(listOf())
    }
}