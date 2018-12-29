package com.faltenreich.releaseradar.ui.adapter

import android.content.Context
import android.view.ViewGroup
import com.faltenreich.releaseradar.data.model.Release
import com.faltenreich.releaseradar.ui.viewholder.ReleaseViewHolder

class ReleaseListAdapter(context: Context) : PagedListAdapter<Release, ReleaseViewHolder>(context, EntityDiffUtilItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReleaseViewHolder = ReleaseViewHolder(context, parent)
}