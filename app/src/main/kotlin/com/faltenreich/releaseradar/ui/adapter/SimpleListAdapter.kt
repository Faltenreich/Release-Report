package com.faltenreich.releaseradar.ui.adapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.faltenreich.releaseradar.ui.viewholder.BaseViewHolder

abstract class SimpleListAdapter <ITEM : Any, VIEWHOLDER : BaseViewHolder<ITEM>>(val context: Context) : RecyclerView.Adapter<VIEWHOLDER>(), ListAdapter<ITEM> {

    override val listItems: ArrayList<ITEM> = ArrayList()

    override fun onBindViewHolder(holder: VIEWHOLDER, position: Int) {
        holder.bind(getListItemAt(position))
    }
}
