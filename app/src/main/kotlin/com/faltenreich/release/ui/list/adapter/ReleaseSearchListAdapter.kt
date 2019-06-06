package com.faltenreich.release.ui.list.adapter

import android.content.Context
import android.view.ViewGroup
import com.faltenreich.release.data.model.Release
import com.faltenreich.release.ui.list.paging.ReleaseDiffUtilCallback
import com.faltenreich.release.ui.list.viewholder.ReleaseDetailViewHolder

class ReleaseSearchListAdapter(context: Context) : PagedListAdapter<Release, ReleaseDetailViewHolder>(context, ReleaseDiffUtilCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ReleaseDetailViewHolder(context, parent)
}