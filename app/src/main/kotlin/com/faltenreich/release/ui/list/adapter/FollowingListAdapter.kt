package com.faltenreich.release.ui.list.adapter

import android.content.Context
import android.view.ViewGroup
import com.faltenreich.release.data.model.Release
import com.faltenreich.release.ui.list.viewholder.ReleaseDetailViewHolder

class FollowingListAdapter(context: Context) : SimpleListAdapter<Release, ReleaseDetailViewHolder>(context) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ReleaseDetailViewHolder(context, parent)
}