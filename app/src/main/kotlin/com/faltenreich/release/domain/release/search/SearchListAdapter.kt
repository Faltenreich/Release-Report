package com.faltenreich.release.domain.release.search

import android.content.Context
import android.view.ViewGroup
import com.faltenreich.release.domain.release.list.ReleaseDetailViewHolder
import com.faltenreich.release.domain.release.list.ReleaseProvider
import com.faltenreich.release.domain.release.list.ReleaseProviderDiffUtilCallback
import com.faltenreich.release.framework.android.adapter.PagedListAdapter
import com.faltenreich.release.framework.android.viewholder.BaseViewHolder

class SearchListAdapter(
    context: Context
) : PagedListAdapter<ReleaseProvider, BaseViewHolder<ReleaseProvider>>(
    context,
    ReleaseProviderDiffUtilCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReleaseDetailViewHolder {
        return ReleaseDetailViewHolder(context, parent)
    }
}