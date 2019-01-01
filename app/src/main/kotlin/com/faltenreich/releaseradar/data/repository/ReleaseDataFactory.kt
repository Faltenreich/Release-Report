package com.faltenreich.releaseradar.data.repository

import androidx.paging.DataSource
import com.faltenreich.releaseradar.ui.adapter.ReleaseListItem

class ReleaseDataFactory(private val onInitialLoad: (() -> Unit)? = null) : DataSource.Factory<String, ReleaseListItem>() {
    override fun create(): DataSource<String, ReleaseListItem> = ReleaseDataSource(onInitialLoad)
}