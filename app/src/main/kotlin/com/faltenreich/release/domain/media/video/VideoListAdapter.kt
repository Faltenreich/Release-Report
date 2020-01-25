package com.faltenreich.release.domain.media.video

import android.content.Context
import android.view.ViewGroup
import com.faltenreich.release.framework.android.adapter.MutableListAdapter

class VideoListAdapter(
    context: Context
) : MutableListAdapter<String, VideoViewHolder>(context) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        return VideoViewHolder(context, parent)
    }
}