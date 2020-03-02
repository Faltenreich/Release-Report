package com.faltenreich.release.framework.android.recyclerview.adapter

import android.content.Context
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.faltenreich.release.framework.android.recyclerview.viewholder.BaseViewHolder

abstract class PagedListAdapter <ITEM : Any, VIEWHOLDER : BaseViewHolder<ITEM>>(
    val context: Context,
    diffCallback: DiffUtil.ItemCallback<ITEM>
) : PagedListAdapter<ITEM, VIEWHOLDER>(diffCallback),
    ListAdapter<ITEM> {

    override val listItems: List<ITEM>
        get() = currentList ?: listOf()

    override fun onBindViewHolder(holder: VIEWHOLDER, position: Int) {
        // Call method from PagedListAdapter instead of ListAdapter in order to kick off pagination
        getItem(position)?.let { item -> holder.bind(item) }
    }
}
