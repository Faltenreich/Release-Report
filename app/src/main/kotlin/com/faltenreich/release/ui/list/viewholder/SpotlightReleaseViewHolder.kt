package com.faltenreich.release.ui.list.viewholder

import android.content.Context
import android.view.ViewGroup
import com.faltenreich.release.R
import com.faltenreich.release.data.model.Release
import com.faltenreich.release.extension.screenSize
import com.faltenreich.release.extension.setImageAsync
import com.faltenreich.release.ui.list.item.SpotlightReleaseItem
import com.faltenreich.release.ui.view.ReleaseOpener
import kotlinx.android.synthetic.main.list_item_release_image.*

class SpotlightReleaseViewHolder(
    context: Context,
    parent: ViewGroup
) : SpotlightViewHolder<SpotlightReleaseItem>(context, R.layout.list_item_release_image, parent), ReleaseOpener {

    override fun onBind(data: SpotlightReleaseItem) {
        val release = data.release
        container.setOnClickListener { openRelease(context, release, releaseCoverImageView) }
        release.imageUrlForThumbnail?.let { imageUrl -> releaseCoverImageView.setImageAsync(imageUrl, context.screenSize.x / 2 ) } ?: releaseCoverImageView.setImageResource(Release.FALLBACK_COVER_COLOR_RES)
        releaseTitleTextView.text = release.artistName?.let { artist -> "$artist - ${release.title}" } ?: release.title
        releaseSubtitleTextView.text = release.releaseDateForUi(context)
    }
}