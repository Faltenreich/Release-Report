package com.faltenreich.release.ui.list.item

import com.faltenreich.release.data.model.Release

sealed class SpotlightItem

data class SpotlightLabelItem(val label: String) : SpotlightItem()

data class SpotlightReleaseItem(val release: Release) : SpotlightItem()

data class SpotlightPromoItem(val release: Release) : SpotlightItem()