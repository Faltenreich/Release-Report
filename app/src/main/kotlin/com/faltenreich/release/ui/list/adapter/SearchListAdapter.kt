package com.faltenreich.release.ui.list.adapter

import android.content.Context
import android.view.ViewGroup
import com.faltenreich.release.ui.list.pagination.ReleaseProviderDiffUtilCallback
import com.faltenreich.release.ui.list.viewholder.BaseViewHolder
import com.faltenreich.release.ui.list.viewholder.ReleaseDetailViewHolder
import com.faltenreich.release.ui.logic.provider.ReleaseProvider

class SearchListAdapter(context: Context) : PagedListAdapter<ReleaseProvider, BaseViewHolder<ReleaseProvider>>(context, ReleaseProviderDiffUtilCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ReleaseDetailViewHolder(context, parent, showDate = true)
}