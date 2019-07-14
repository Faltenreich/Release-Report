package com.faltenreich.release.ui.list.item

import com.faltenreich.release.data.model.Release
import org.threeten.bp.LocalDate

data class ReleaseItem(
    override val date: LocalDate,
    val release: Release
) : DateItem