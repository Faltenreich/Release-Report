package com.faltenreich.releaseradar.ui.list.adapter

import android.content.Context
import android.view.ViewGroup
import com.faltenreich.releaseradar.data.model.Release
import com.faltenreich.releaseradar.ui.list.paging.ReleaseDiffUtilCallback
import com.faltenreich.releaseradar.ui.list.viewholder.SpotlightViewHolder

class SpotlightListAdapter(context: Context) : PagedListAdapter<Release, SpotlightViewHolder>(context, ReleaseDiffUtilCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = SpotlightViewHolder(context, parent)
}