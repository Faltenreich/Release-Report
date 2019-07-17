package com.faltenreich.release.ui.list.item

import com.faltenreich.release.data.model.Release
import com.faltenreich.release.ui.list.provider.ReleaseProvider

sealed class SpotlightItem

data class SpotlightLabelItem(val label: String) : SpotlightItem()

data class SpotlightReleaseItem(override val release: Release) : SpotlightItem(),
    ReleaseProvider

data class SpotlightPromoItem(val release: Release) : SpotlightItem()