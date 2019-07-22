package com.faltenreich.release.ui.list.viewholder

import android.content.Context
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintSet
import com.faltenreich.release.R
import com.faltenreich.release.data.model.Release
import com.faltenreich.release.extension.screenSize
import com.faltenreich.release.extension.setImageAsync
import kotlinx.android.synthetic.main.list_item_release_more_item.*

class ReleaseMoreItemViewHolder(
    context: Context,
    parent: ViewGroup
) : BaseViewHolder<Release>(context, R.layout.list_item_release_more_item, parent) {
    override fun onBind(data: Release) {
        data.imageUrlForThumbnail?.let { imageUrl ->
            moreItemImageView.setImageAsync(imageUrl)
        } ?: moreItemImageView.setImageResource(Release.FALLBACK_COVER_COLOR_RES)
    }
}