package com.faltenreich.release.ui.list.adapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.faltenreich.release.ui.list.viewholder.BaseViewHolder

abstract class SimpleListAdapter <ITEM : Any, VIEWHOLDER : BaseViewHolder<ITEM>>(val context: Context) : RecyclerView.Adapter<VIEWHOLDER>(), ListAdapter<ITEM> {

    override val listItems: ArrayList<ITEM> = ArrayList()

    override fun onBindViewHolder(holder: VIEWHOLDER, position: Int) {
        getListItemAt(position)?.let { item -> holder.bind(item) }
    }

    override fun getItemCount(): Int = listItems.size
}
