package com.faltenreich.release.base.pagination

import org.threeten.bp.LocalDate

data class PaginationInfo(
    val page: Int,
    val pageSize: Int,
    val descending: Boolean,
    val previousDate: LocalDate?
)