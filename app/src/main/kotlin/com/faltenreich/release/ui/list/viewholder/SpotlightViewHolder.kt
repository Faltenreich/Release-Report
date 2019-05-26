package com.faltenreich.release.ui.list.viewholder

import android.content.Context
import android.view.ViewGroup
import com.faltenreich.release.R
import com.faltenreich.release.data.model.Release
import com.faltenreich.release.extension.screenSize
import com.faltenreich.release.extension.setImageAsync
import com.faltenreich.release.ui.view.ReleaseOpener
import kotlinx.android.synthetic.main.list_item_spotlight.*

class SpotlightViewHolder(context: Context, parent: ViewGroup) : BaseViewHolder<Release>(context, R.layout.list_item_spotlight, parent), ReleaseOpener {

    override fun onBind(data: Release) {
        container.setOnClickListener { openRelease(context, data, releaseCoverImageView) }
        data.imageUrlForThumbnail?.let { imageUrl -> releaseCoverImageView.setImageAsync(imageUrl, context.screenSize.x / 2 ) } ?: releaseCoverImageView.setImageResource(Release.FALLBACK_COVER_COLOR_RES)
        releaseNameTextView.text = data.artistName?.let { artist -> "$artist - ${data.title}" } ?: data.title
        releaseDateTextView.text = data.releaseDateForUi(context)
    }
}