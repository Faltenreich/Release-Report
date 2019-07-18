package com.faltenreich.release.ui.list.item

import com.faltenreich.release.ui.logic.provider.DateProvider
import org.threeten.bp.LocalDate

data class ReleaseEmptyItem(override val date: LocalDate) : DateProvider