package com.faltenreich.release.ui.list.adapter

import android.content.Context
import android.view.ViewGroup
import com.faltenreich.release.ui.list.item.SpotlightItem
import com.faltenreich.release.ui.list.viewholder.SpotlightViewHolder

class SpotlightListAdapter(context: Context) : MutableListAdapter<SpotlightItem, SpotlightViewHolder>(context) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = SpotlightViewHolder(context, parent)
}