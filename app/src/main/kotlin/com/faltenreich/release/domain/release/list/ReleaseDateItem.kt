package com.faltenreich.release.domain.release.list

import com.faltenreich.release.domain.date.DateProvider
import org.threeten.bp.LocalDate

data class ReleaseDateItem(override val date: LocalDate) :
    DateProvider