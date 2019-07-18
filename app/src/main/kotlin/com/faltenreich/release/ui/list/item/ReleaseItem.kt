package com.faltenreich.release.ui.list.item

import com.faltenreich.release.data.model.Release
import com.faltenreich.release.ui.list.provider.DateProvider
import com.faltenreich.release.ui.list.provider.ReleaseProvider
import org.threeten.bp.LocalDate

data class ReleaseItem(
    override val date: LocalDate,
    override val release: Release,
    override val dateStyle: DateStyle = DateStyle.NONE
) : DateProvider, ReleaseProvider