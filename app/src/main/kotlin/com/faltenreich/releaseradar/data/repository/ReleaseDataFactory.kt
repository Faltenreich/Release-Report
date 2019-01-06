package com.faltenreich.releaseradar.data.repository

import androidx.paging.DataSource
import androidx.paging.ItemKeyedDataSource
import androidx.paging.PagedList
import com.faltenreich.releaseradar.ui.adapter.ReleaseListItem

class ReleaseDataFactory(private val dataSource: ItemKeyedDataSource<String, ReleaseListItem>) : DataSource.Factory<String, ReleaseListItem>() {
    val config by lazy { PagedList.Config.Builder().setInitialLoadSizeHint(PAGE_SIZE * 3).setPageSize(PAGE_SIZE).setPrefetchDistance(PAGE_SIZE / 2).build() }

    override fun create(): DataSource<String, ReleaseListItem> = dataSource

    companion object {
        private const val PAGE_SIZE = 30
    }
}