package com.faltenreich.releaseradar.ui.list.adapter

import android.content.Context
import android.view.ViewGroup
import com.faltenreich.releaseradar.data.model.Release
import com.faltenreich.releaseradar.ui.list.viewholder.ReleaseDetailViewHolder

class FollowingListAdapter(context: Context) : SimpleListAdapter<Release, ReleaseDetailViewHolder>(context) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ReleaseDetailViewHolder(context, parent)
}