package com.faltenreich.releaseradar.data.repository

import androidx.paging.DataSource
import androidx.paging.ItemKeyedDataSource
import androidx.paging.PagedList

class ReleaseDataFactory<T : Any>(private val dataSource: ItemKeyedDataSource<String, T>) : DataSource.Factory<String, T>() {
    val config by lazy { PagedList.Config.Builder().setInitialLoadSizeHint(PAGE_SIZE * 3).setPageSize(PAGE_SIZE).setPrefetchDistance(PAGE_SIZE / 2).build() }

    override fun create(): DataSource<String, T> = dataSource

    companion object {
        private const val PAGE_SIZE = 30
    }
}