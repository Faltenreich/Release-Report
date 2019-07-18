package com.faltenreich.release.ui.list.viewholder

import android.content.Context
import android.view.ViewGroup
import com.faltenreich.release.R
import com.faltenreich.release.data.model.Release
import com.faltenreich.release.extension.screenSize
import com.faltenreich.release.extension.setImageAsync
import com.faltenreich.release.ui.list.item.SpotlightPromoItem
import com.faltenreich.release.ui.logic.opener.ReleaseOpener
import kotlinx.android.synthetic.main.list_item_release_promo.*

class ReleasePromoViewHolder(
    context: Context,
    parent: ViewGroup
) : BaseViewHolder<SpotlightPromoItem>(context, R.layout.list_item_release_promo, parent), ReleaseOpener {
    override fun onBind(data: SpotlightPromoItem) {
        val release = data.release
        container.setOnClickListener { openRelease(release, releaseCoverImageView) }
        release.imageUrlForWallpaper?.let { imageUrl -> releaseCoverImageView.setImageAsync(imageUrl, context.screenSize.x / 2 ) } ?: releaseCoverImageView.setImageResource(Release.FALLBACK_COVER_COLOR_RES)
        releaseTitleTextView.text = release.artistName?.let { artist -> "$artist - ${release.title}" } ?: release.title
    }
}