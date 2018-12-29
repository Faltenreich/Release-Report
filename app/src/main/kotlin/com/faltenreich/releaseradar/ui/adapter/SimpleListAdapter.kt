package com.faltenreich.releaseradar.ui.adapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.faltenreich.releaseradar.ui.viewholder.BaseViewHolder

abstract class SimpleListAdapter <MODEL : Any, VIEWHOLDER : BaseViewHolder<MODEL>>(val context: Context) : RecyclerView.Adapter<VIEWHOLDER>(), ListAdapter<MODEL> {

    override val items: ArrayList<MODEL> = ArrayList()

    override fun onBindViewHolder(holder: VIEWHOLDER, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemCount(): Int = super.getItemCount()
}
