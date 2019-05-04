package com.faltenreich.releaseradar.ui.list.paging

import com.faltenreich.releaseradar.data.model.Release
import com.faltenreich.releaseradar.data.repository.ReleaseRepository
import org.threeten.bp.LocalDate

class ReleaseSearchDataSource(private val query: String?, private val onInitialLoad: ((List<Release>) -> Unit)? = null) : PagingDataSource<Release>() {
    private var startAtDate: LocalDate = LocalDate.now()

    override fun getKey(item: Release): String = item.id ?: ""

    override fun loadInitial(params: LoadInitialParams<String>, callback: LoadInitialCallback<Release>) = load(params.requestedLoadSize, callback, onInitialLoad)

    override fun loadBefore(params: LoadParams<String>, callback: LoadCallback<Release>) = Unit

    override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<Release>) = load(params.requestedLoadSize, callback)

    private fun load(requestedLoadSize: Int, callback: LoadCallback<Release>, onLoad: ((List<Release>) -> Unit)? = null) {
        query?.let {
            ReleaseRepository.search(query, onSuccess = { releases ->
                callback.onResult(releases)
                // TODO: Paging
            }, onError = {
                val result = listOf<Release>()
                onLoad?.invoke(result)
                callback.onResult(result)
            })
        } ?: callback.onResult(listOf())
    }
}