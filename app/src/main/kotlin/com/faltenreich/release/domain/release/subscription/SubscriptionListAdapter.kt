package com.faltenreich.release.domain.release.subscription

import android.content.Context
import android.view.ViewGroup
import com.faltenreich.release.domain.release.list.ReleaseDetailViewHolder
import com.faltenreich.release.domain.release.list.ReleaseProvider
import com.faltenreich.release.framework.android.view.recyclerview.adapter.MutableListAdapter
import com.faltenreich.release.framework.android.view.recyclerview.viewholder.BaseViewHolder

class SubscriptionListAdapter(
    context: Context
) : MutableListAdapter<ReleaseProvider, BaseViewHolder<ReleaseProvider>>(context) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReleaseDetailViewHolder {
        return ReleaseDetailViewHolder(context, parent)
    }
}