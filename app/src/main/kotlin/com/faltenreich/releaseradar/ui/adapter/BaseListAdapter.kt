package com.faltenreich.releaseradar.ui.adapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.faltenreich.releaseradar.ui.viewholder.BaseViewHolder

abstract class BaseListAdapter <MODEL : Any, VIEWHOLDER : BaseViewHolder<MODEL>>(val context: Context) : RecyclerView.Adapter<VIEWHOLDER>() {

    private val internalItems: ArrayList<MODEL> = ArrayList()

    val items: List<MODEL>
        get() = internalItems.toList()

    override fun onBindViewHolder(holder: VIEWHOLDER, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemCount(): Int = internalItems.size

    fun getItem(position: Int): MODEL = internalItems[position]

    fun add(item: MODEL) = internalItems.add(item)

    fun addAt(position: Int, item: MODEL) = internalItems.add(position, item)

    fun addAll(items: List<MODEL>) = internalItems.addAll(items)

    fun remove(item: MODEL) = internalItems.remove(item)

    fun removeAt(position: Int) = internalItems.removeAt(position)

    fun removeAll() = internalItems.clear()

    fun clear() = removeAll()
}
