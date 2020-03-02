package com.faltenreich.release.framework.android.recycler.adapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.faltenreich.release.framework.android.recycler.viewholder.BaseViewHolder

abstract class MutableListAdapter <ITEM : Any, VIEW : BaseViewHolder<ITEM>>(
    val context: Context
) : RecyclerView.Adapter<VIEW>(), ListAdapter<ITEM>, MutableCollectionAdapter<ITEM> {

    override val listItems = ArrayList<ITEM>()

    override fun onBindViewHolder(holder: VIEW, position: Int) {
        getListItemAt(position)?.let { item -> holder.bind(item) }
    }

    override fun getItemCount(): Int = listItems.size
}
