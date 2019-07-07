package com.faltenreich.release.ui.list.adapter

import android.content.Context
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.faltenreich.release.extension.className
import com.faltenreich.release.ui.list.viewholder.BaseViewHolder

abstract class SimpleListAdapter <ITEM : Any, VIEWHOLDER : BaseViewHolder<ITEM>>(val context: Context) : RecyclerView.Adapter<VIEWHOLDER>(), ListAdapter<ITEM> {

    override val listItems = ArrayList<ITEM>()

    override fun onBindViewHolder(holder: VIEWHOLDER, position: Int) {
        getListItemAt(position)?.let { item -> holder.bind(item) }
    }

    override fun getItemCount(): Int = listItems.size

    fun addListItem(item: ITEM) = listItems.add(item)

    fun addListItemAt(position: Int, item: ITEM) = try { listItems.add(position, item) } catch (exception: IndexOutOfBoundsException) { Log.e(className, exception.message) }

    fun addListItems(items: List<ITEM>) = this.listItems.addAll(items)

    fun removeListItem(item: ITEM) = listItems.remove(item)

    fun removeListItemAt(position: Int) = try { listItems.removeAt(position) } catch (exception: IndexOutOfBoundsException) { Log.e(className, exception.message) }

    fun removeListItems() = listItems.clear()
}
