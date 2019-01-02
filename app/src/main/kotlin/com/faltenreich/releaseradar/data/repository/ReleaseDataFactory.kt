package com.faltenreich.releaseradar.data.repository

import androidx.paging.DataSource
import androidx.paging.PagedList
import com.faltenreich.releaseradar.ui.adapter.ReleaseListItem

class ReleaseDataFactory(private val onInitialLoad: (() -> Unit)? = null) : DataSource.Factory<String, ReleaseListItem>() {
    val config by lazy { PagedList.Config.Builder().setInitialLoadSizeHint(PAGE_SIZE).setPageSize(PAGE_SIZE).setPrefetchDistance(PAGE_SIZE / 2).build() }

    override fun create(): DataSource<String, ReleaseListItem> = ReleaseDataSource(onInitialLoad)

    companion object {
        private const val PAGE_SIZE = 60
    }
}