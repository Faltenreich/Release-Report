package com.faltenreich.release.domain.release.detail

import android.content.Context
import android.view.ViewGroup
import com.faltenreich.release.R
import com.faltenreich.release.framework.android.view.setImageAsync
import com.faltenreich.release.framework.android.viewholder.BaseViewHolder
import kotlinx.android.synthetic.main.list_item_release_promo.*

class ImageViewHolder(
    context: Context,
    parent: ViewGroup
) : BaseViewHolder<String>(
    context,
    R.layout.list_item_image,
    parent
), UrlOpener {

    init {
        imageView.setOnClickListener { openUrl(context, data) }
    }

    override fun onBind(data: String) {
        imageView.setImageAsync(data, imageView.width)
    }
}