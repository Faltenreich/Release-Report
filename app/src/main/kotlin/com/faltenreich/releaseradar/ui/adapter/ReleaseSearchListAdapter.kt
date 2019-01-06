package com.faltenreich.releaseradar.ui.adapter

import android.content.Context
import android.view.ViewGroup
import com.faltenreich.releaseradar.ui.viewholder.ReleaseSearchViewHolder

class ReleaseSearchListAdapter(context: Context) : PagedListAdapter<ReleaseListItem, ReleaseSearchViewHolder>(context, ReleaseListDiffUtilItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReleaseSearchViewHolder = ReleaseSearchViewHolder(context, parent)
}