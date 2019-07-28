package com.faltenreich.release.ui.list.adapter

import android.content.Context
import android.view.ViewGroup
import com.faltenreich.release.data.model.Release
import com.faltenreich.release.ui.list.viewholder.ReleaseMoreItemViewHolder

class MoreListAdapter(context: Context) : MutableListAdapter<Release, ReleaseMoreItemViewHolder>(context) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ReleaseMoreItemViewHolder(context, parent)
}