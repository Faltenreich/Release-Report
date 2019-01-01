package com.faltenreich.releaseradar.ui.adapter

import android.content.Context
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.faltenreich.releaseradar.ui.viewholder.BaseViewHolder

abstract class PagedListAdapter <ITEM : Any, VIEWHOLDER : BaseViewHolder<ITEM>>(val context: Context, diffCallback: DiffUtil.ItemCallback<ITEM>) : PagedListAdapter<ITEM, VIEWHOLDER>(diffCallback) {

    override fun onBindViewHolder(holder: VIEWHOLDER, position: Int) {
        getItem(position)?.let { item -> holder.bind(item) }
    }
}
