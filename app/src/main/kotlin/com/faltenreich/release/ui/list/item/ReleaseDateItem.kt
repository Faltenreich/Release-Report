package com.faltenreich.release.ui.list.item

import com.faltenreich.release.ui.logic.provider.DateProvider
import org.threeten.bp.LocalDate

data class ReleaseDateItem(override val date: LocalDate) : DateProvider