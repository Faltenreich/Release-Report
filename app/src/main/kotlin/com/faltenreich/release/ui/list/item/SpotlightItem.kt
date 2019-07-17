package com.faltenreich.release.ui.list.item

import android.content.Context
import com.faltenreich.release.data.model.Release
import com.faltenreich.release.ui.list.provider.LabelProvider
import com.faltenreich.release.ui.list.provider.ReleaseProvider

sealed class SpotlightItem

data class SpotlightHeaderItem(val labelResId: Int) : SpotlightItem(), LabelProvider {
    override fun print(context: Context): String? = context.getString(labelResId)
}

data class SpotlightReleaseItem(override val release: Release) : SpotlightItem(), ReleaseProvider

data class SpotlightPromoItem(override val release: Release) : SpotlightItem(), ReleaseProvider