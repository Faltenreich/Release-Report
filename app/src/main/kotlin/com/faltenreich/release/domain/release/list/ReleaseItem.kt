package com.faltenreich.release.domain.release.list

import com.faltenreich.release.data.model.Release
import com.faltenreich.release.domain.date.DateProvider
import org.threeten.bp.LocalDate

data class ReleaseItem(
    override val release: Release,
    override val date: LocalDate
) : DateProvider, ReleaseProvider