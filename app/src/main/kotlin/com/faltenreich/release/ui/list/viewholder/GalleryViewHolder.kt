package com.faltenreich.release.ui.list.viewholder

import android.content.Context
import android.view.ViewGroup
import com.faltenreich.release.R
import com.faltenreich.release.data.model.Image
import com.faltenreich.release.data.model.Release
import com.faltenreich.release.extension.screenSize
import com.faltenreich.release.extension.setImageAsync
import kotlinx.android.synthetic.main.list_item_gallery.*

class GalleryViewHolder(context: Context, parent: ViewGroup) : BaseViewHolder<Image>(context, R.layout.list_item_gallery, parent) {

    override fun onBind(data: Image) {
        data.url?.let { url -> imageView.setImageAsync(url, context.screenSize.x / 2 ) } ?: imageView.setImageResource(Release.FALLBACK_COVER_COLOR_RES)
    }
}