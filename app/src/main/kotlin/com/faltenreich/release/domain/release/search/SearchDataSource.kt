package com.faltenreich.release.domain.release.search

import androidx.paging.PageKeyedDataSource
import com.faltenreich.release.data.repository.ReleaseRepository
import com.faltenreich.release.domain.release.list.ReleaseItem
import com.faltenreich.release.domain.release.list.ReleaseProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchDataSource(
    private val query: String,
    private val scope: CoroutineScope,
    private val afterInitialLoad: (data: List<ReleaseProvider>) -> Unit
) : PageKeyedDataSource<Int, ReleaseProvider>() {

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, ReleaseProvider>) {
        load(0, params.requestedLoadSize, object : LoadCallback<Int, ReleaseProvider>() {
            override fun onResult(data: List<ReleaseProvider>, adjacentPageKey: Int?) {
                callback.onResult(data, 0, 1)
                afterInitialLoad(data)
            }
        })
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, ReleaseProvider>) = Unit

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, ReleaseProvider>) {
        load(params.key, params.requestedLoadSize, callback)
    }

    private fun load(page: Int, pageSize: Int, callback: LoadCallback<Int, ReleaseProvider>) {
        scope.launch(Dispatchers.IO) {
            val releases = ReleaseRepository.search(query, page, pageSize)
            val items = releases.mapNotNull { release ->
                release.releaseDate?.let { date ->
                    ReleaseItem(
                        release,
                        date
                    )
                }
            }
            scope.launch(Dispatchers.Main) {
                callback.onResult(items, page + 1)
            }
        }
    }
}