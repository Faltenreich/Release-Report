package com.faltenreich.release.ui.list.pagination

import androidx.paging.PageKeyedDataSource
import com.faltenreich.release.data.repository.ReleaseRepository
import com.faltenreich.release.ui.list.item.ReleaseItem
import com.faltenreich.release.ui.logic.provider.ReleaseProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SearchDataSource(
    private val query: String,
    private val afterLoadInitial: (Int) -> Unit
) : PageKeyedDataSource<Int, ReleaseProvider>() {

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, ReleaseProvider>) {
        load(0, params.requestedLoadSize, object : LoadCallback<Int, ReleaseProvider>() {
            override fun onResult(data: MutableList<ReleaseProvider>, adjacentPageKey: Int?) {
                callback.onResult(data, 0, 1)
                afterLoadInitial(data.size)
            }
        })
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, ReleaseProvider>) = Unit

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, ReleaseProvider>) {
        load(params.key, params.requestedLoadSize, callback)
    }

    private fun load(page: Int, pageSize: Int, callback: LoadCallback<Int, ReleaseProvider>) {
        ReleaseRepository.search(query, page, pageSize) { releases ->
            GlobalScope.launch {
                val items = releases.mapNotNull { release ->
                    release.releaseDate?.let { date -> ReleaseItem(release, date) }
                }
                GlobalScope.launch(Dispatchers.Main) { callback.onResult(items, page + 1) }
            }
        }
    }
}