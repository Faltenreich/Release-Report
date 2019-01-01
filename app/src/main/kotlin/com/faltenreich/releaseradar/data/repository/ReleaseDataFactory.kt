package com.faltenreich.releaseradar.data.repository

import androidx.paging.DataSource
import com.faltenreich.releaseradar.data.model.Release

class ReleaseDataFactory(private val onInitialLoad: (() -> Unit)? = null) : DataSource.Factory<String, Release>() {
    override fun create(): DataSource<String, Release> = ReleaseDataSource(onInitialLoad)
}