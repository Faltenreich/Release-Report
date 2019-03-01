package com.faltenreich.releaseradar.ui.list.adapter

import android.content.Context
import android.view.ViewGroup
import com.faltenreich.releaseradar.data.model.Release
import com.faltenreich.releaseradar.ui.list.viewholder.ReleaseSearchViewHolder

class FollowingListAdapter(context: Context) : SimpleListAdapter<Release, ReleaseSearchViewHolder>(context) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ReleaseSearchViewHolder(context, parent)
}