package com.faltenreich.release.base.pagination

import androidx.paging.DataSource
import androidx.paging.PagedList

class PagingDataFactory<Key : Any, Value : Any>(
    private val dataSource: DataSource<Key, Value>,
    pageSize: Int = PAGE_SIZE
) : DataSource.Factory<Key, Value>() {

    val config by lazy {
        PagedList.Config.Builder()
            .setInitialLoadSizeHint(pageSize)
            .setPageSize(pageSize)
            .setPrefetchDistance(pageSize)
            .build()
    }

    override fun create(): DataSource<Key, Value> = dataSource

    companion object {
        private const val PAGE_SIZE = 50
    }
}