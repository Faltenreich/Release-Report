package com.faltenreich.release.ui.list.pagination

import androidx.paging.DataSource
import androidx.paging.PagedList

class PagingDataFactory<Key, Value : Any>(
    private val dataSource: DataSource<Key, Value>,
    pageSize: Int
) : DataSource.Factory<Key, Value>() {

    val config by lazy {
        PagedList.Config.Builder()
            .setInitialLoadSizeHint(pageSize * 3)
            .setPageSize(pageSize)
            .setPrefetchDistance(pageSize)
            .build()
    }

    override fun create(): DataSource<Key, Value> = dataSource
}