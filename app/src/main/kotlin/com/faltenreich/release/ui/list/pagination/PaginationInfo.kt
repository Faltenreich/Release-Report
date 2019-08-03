package com.faltenreich.release.ui.list.pagination

import org.threeten.bp.LocalDate

data class PaginationInfo(
    val page: Int,
    val pageSize: Int,
    val descending: Boolean,
    val previousDate: LocalDate?
)