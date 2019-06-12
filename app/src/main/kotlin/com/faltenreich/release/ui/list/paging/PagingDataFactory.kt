package com.faltenreich.release.ui.list.paging

import androidx.paging.DataSource
import androidx.paging.PagedList

class PagingDataFactory<Key, Value : Any>(private val dataSource: DataSource<Key, Value>) : DataSource.Factory<Key, Value>() {

    val config by lazy {
        PagedList.Config.Builder()
            .setInitialLoadSizeHint(PAGE_SIZE * 3)
            .setPageSize(PAGE_SIZE)
            .setPrefetchDistance(PAGE_SIZE / 2)
            .build()
    }

    override fun create(): DataSource<Key, Value> = dataSource

    companion object {
        private const val PAGE_SIZE = 30
    }
}