package com.faltenreich.release.ui.list.item

import com.faltenreich.release.data.model.Release
import com.faltenreich.release.ui.list.provider.DateProvider
import org.threeten.bp.LocalDate

data class ReleaseMoreItem(override val date: LocalDate, val releases: List<Release>) : DateProvider