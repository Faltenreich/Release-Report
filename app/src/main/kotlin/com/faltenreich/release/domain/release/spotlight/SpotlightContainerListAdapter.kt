package com.faltenreich.release.domain.release.spotlight

import android.content.Context
import android.view.ViewGroup
import com.faltenreich.release.domain.release.list.ReleaseDetailViewHolder
import com.faltenreich.release.domain.release.list.ReleaseProvider
import com.faltenreich.release.framework.android.recycler.adapter.MutableListAdapter

class SpotlightContainerListAdapter(
    context: Context
) : MutableListAdapter<ReleaseProvider, ReleaseDetailViewHolder>(context) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReleaseDetailViewHolder {
        return ReleaseDetailViewHolder(context, parent)
    }
}