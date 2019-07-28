package com.faltenreich.release.ui.list.item

import android.content.Context
import androidx.annotation.StringRes
import com.faltenreich.release.data.model.Release
import com.faltenreich.release.ui.logic.provider.LabelProvider
import com.faltenreich.release.ui.logic.provider.ReleaseProvider

sealed class SpotlightItem

data class SpotlightReleaseItem(
    @StringRes val headerResId: Int,
    val releases: List<ReleaseProvider>
) : SpotlightItem(), LabelProvider {
    override fun print(context: Context): String? = context.getString(headerResId)
}

data class SpotlightPromoItem(
    override val release: Release
) : SpotlightItem(), ReleaseProvider