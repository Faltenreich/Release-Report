package com.faltenreich.releaseradar.ui.list.paging

import androidx.paging.ItemKeyedDataSource

abstract class PagingDataSource<T : Any> : ItemKeyedDataSource<String, T>()