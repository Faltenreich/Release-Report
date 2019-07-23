package com.faltenreich.release.ui.list.item

import com.faltenreich.release.data.model.Release
import com.faltenreich.release.ui.logic.provider.DateProvider
import com.faltenreich.release.ui.logic.provider.ReleaseProvider
import org.threeten.bp.LocalDate

data class ReleaseItem(
    override val date: LocalDate,
    override val release: Release
) : DateProvider, ReleaseProvider