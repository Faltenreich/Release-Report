package com.faltenreich.release.ui.list.pagination

import androidx.paging.PageKeyedDataSource
import com.faltenreich.release.data.model.Release
import com.faltenreich.release.data.repository.ReleaseRepository
import com.faltenreich.release.data.repository.RepositoryFactory

class SearchDataSource(private val query: String?, private val onInitialLoad: ((List<Release>) -> Unit)? = null) : PageKeyedDataSource<Int, Release>() {
    private val releaseRepository = RepositoryFactory.repository<ReleaseRepository>()

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Release>) {
        load(0, params.requestedLoadSize, object : LoadCallback<Int, Release>() {
            override fun onResult(data: MutableList<Release>, adjacentPageKey: Int?) {
                onInitialLoad?.invoke(data)
                callback.onResult(data, 0, 1)
            }
        }, onInitialLoad)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Release>) = Unit

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Release>) {
        load(params.key, params.requestedLoadSize, callback)
    }

    private fun load(page: Int, pageSize: Int, callback: LoadCallback<Int, Release>, onLoad: ((List<Release>) -> Unit)? = null) {
        val onResponse = { data: List<Release> ->
            onLoad?.invoke(data)
            callback.onResult(data, page + 1)
        }
        query?.let { query -> releaseRepository.search(query, page, pageSize) { releases -> onResponse(releases) } } ?: onResponse(listOf())
    }
}