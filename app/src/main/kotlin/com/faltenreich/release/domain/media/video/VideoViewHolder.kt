package com.faltenreich.release.domain.media.video

import android.content.Context
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.faltenreich.release.R
import com.faltenreich.release.base.intent.UrlOpener
import com.faltenreich.release.framework.android.view.setImageAsync
import com.faltenreich.release.framework.android.viewholder.BaseViewHolder
import kotlinx.android.synthetic.main.list_item_video.*

class VideoViewHolder(
    context: Context,
    parent: ViewGroup
) : BaseViewHolder<String>(
    context,
    R.layout.list_item_video,
    parent
), UrlOpener {

    init {
        rootView.setOnClickListener { openUrl(context, data) }
    }

    override fun onBind(data: String) {
        VideoThumbnailFactory.createThumbnail(data)?.let { imageUrl ->
            progressView.isVisible = true
            videoView.setImageAsync(imageUrl, videoView.width) {
                progressView.isVisible = false
            }
        } ?: videoView.setImageResource(android.R.color.black)
    }
}