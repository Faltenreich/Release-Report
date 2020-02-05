package com.faltenreich.release.domain.media.image

import android.content.Context
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.faltenreich.release.R
import com.faltenreich.release.framework.android.view.setImageAsync
import com.faltenreich.release.framework.android.viewholder.BaseViewHolder
import kotlinx.android.synthetic.main.list_item_image.*

class ImageViewHolder(
    context: Context,
    parent: ViewGroup,
    private val onImageSelected: (String) -> Unit
) : BaseViewHolder<String>(
    context,
    R.layout.list_item_image,
    parent
) {

    init {
        imageView.setOnClickListener { onImageSelected(data) }
    }

    override fun onBind(data: String) {
        progressView.isVisible = true
        imageView.setImageAsync(data, imageView.width) {
            progressView.isVisible = false
        }
    }
}