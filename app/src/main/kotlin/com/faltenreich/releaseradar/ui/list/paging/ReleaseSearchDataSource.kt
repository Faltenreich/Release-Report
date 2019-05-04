package com.faltenreich.releaseradar.ui.list.paging

import com.faltenreich.releaseradar.data.model.Release

class ReleaseSearchDataSource(private val query: String?, private val onInitialLoad: ((List<Release>) -> Unit)? = null) : PagingDataSource<Release>() {

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Release>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Release>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Release>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /*
    private var startAtDate: LocalDate = LocalDate.now()

    override fun getKey(item: Release): String = item.id ?: ""

    override fun loadInitial(params: LoadInitialParams<String>, callback: LoadInitialCallback<Release>) = load(params.requestedLoadSize, callback, onInitialLoad)

    override fun loadBefore(params: LoadParams<String>, callback: LoadCallback<Release>) = Unit

    override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<Release>) = load(params.requestedLoadSize, callback)

    private fun load(requestedLoadSize: Int, callback: LoadCallback<Release>, onLoad: ((List<Release>) -> Unit)? = null) {
        query?.let { query ->
            ReleaseRepository.search(query, onSuccess = { releases ->
                onLoad?.invoke(releases)
                callback.onResult(releases)
                // TODO: Paging
            }, onError = {
                val result = listOf<Release>()
                onLoad?.invoke(result)
                callback.onResult(result)
            })
        } ?: callback.onResult(listOf())
    }
    */
}