package com.faltenreich.release.ui.list.adapter

import android.content.Context
import android.view.ViewGroup
import com.faltenreich.release.ui.list.viewholder.ReleaseImageViewHolder
import com.faltenreich.release.ui.logic.provider.ReleaseProvider

class SpotlightContainerListAdapter(context: Context) : MutableListAdapter<ReleaseProvider, ReleaseImageViewHolder>(context) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ReleaseImageViewHolder(context, parent)
}