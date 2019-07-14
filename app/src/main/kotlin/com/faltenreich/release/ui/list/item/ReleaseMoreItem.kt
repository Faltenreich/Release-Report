package com.faltenreich.release.ui.list.item

import org.threeten.bp.LocalDate

data class ReleaseMoreItem(
    override val date: LocalDate,
    val count: Int
) : DateItem