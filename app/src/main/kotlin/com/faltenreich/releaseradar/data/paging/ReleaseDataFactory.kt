package com.faltenreich.releaseradar.data.paging

import androidx.paging.DataSource
import com.faltenreich.releaseradar.data.model.Release

object ReleaseDataFactory : DataSource.Factory<String, Release>() {
    override fun create(): DataSource<String, Release> = ReleaseDataSource
}