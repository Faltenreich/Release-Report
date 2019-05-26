package com.faltenreich.release.ui.list.adapter

import android.content.Context
import android.view.ViewGroup
import com.faltenreich.release.data.model.Release
import com.faltenreich.release.ui.list.viewholder.SpotlightViewHolder

class SpotlightListAdapter(context: Context) : SimpleListAdapter<Release, SpotlightViewHolder>(context) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = SpotlightViewHolder(context, parent)
}