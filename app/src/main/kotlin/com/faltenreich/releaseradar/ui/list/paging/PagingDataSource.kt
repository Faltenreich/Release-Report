package com.faltenreich.releaseradar.ui.list.paging

import androidx.paging.PageKeyedDataSource

abstract class PagingDataSource<T : Any> : PageKeyedDataSource<Int, T>()