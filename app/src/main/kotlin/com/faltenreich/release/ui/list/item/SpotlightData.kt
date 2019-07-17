package com.faltenreich.release.ui.list.item

import com.faltenreich.release.data.model.Release

data class SpotlightData(
    val promo: Release? = null,
    val weekly: List<Release>? = null,
    val recent: List<Release>? = null,
    val favorite: List<Release>? = null
)