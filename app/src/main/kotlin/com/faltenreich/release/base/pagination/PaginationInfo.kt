package com.faltenreich.release.base.pagination

import androidx.paging.PageKeyedDataSource
import org.threeten.bp.LocalDate

data class PaginationInfo(
    val page: Int,
    val pageSize: Int,
    val descending: Boolean,
    val previousDate: LocalDate?
) {
    constructor(params: PageKeyedDataSource.LoadInitialParams<*>, descending: Boolean) : this(
        0,
        params.requestedLoadSize,
        descending,
        null
    )
}