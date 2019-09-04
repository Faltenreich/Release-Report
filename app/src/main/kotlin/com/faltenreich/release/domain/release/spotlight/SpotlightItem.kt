package com.faltenreich.release.domain.release.spotlight

import android.content.Context
import androidx.annotation.StringRes
import com.faltenreich.release.data.model.Release
import com.faltenreich.release.data.provider.LabelProvider
import com.faltenreich.release.domain.release.list.ReleaseProvider

sealed class SpotlightItem

data class SpotlightReleaseItem(
    @StringRes val headerResId: Int,
    val releases: List<ReleaseProvider>,
    val totalReleaseCount: Int?
) : SpotlightItem(), LabelProvider {
    override fun print(context: Context): String? = context.getString(headerResId)
}

data class SpotlightPromoItem(
    override val release: Release
) : SpotlightItem(), ReleaseProvider