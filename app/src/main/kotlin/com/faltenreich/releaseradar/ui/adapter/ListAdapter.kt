package com.faltenreich.releaseradar.ui.adapter

import android.util.Log
import com.faltenreich.releaseradar.extension.className

interface ListAdapter <ITEM : Any> {
    val listItems: ArrayList<ITEM>

    fun getListItemCount(): Int = listItems.size

    fun getListItemAt(position: Int): ITEM? = listItems.getOrNull(position)

    fun addListItem(item: ITEM) = listItems.add(item)

    fun addListItemAt(position: Int, item: ITEM) = try { listItems.add(position, item) } catch (exception: IndexOutOfBoundsException) { Log.e(className, exception.message) }

    fun addListItems(items: List<ITEM>) = this.listItems.addAll(items)

    fun removeListItem(item: ITEM) = listItems.remove(item)

    fun removeListItemAt(position: Int) = try { listItems.removeAt(position) } catch (exception: IndexOutOfBoundsException) { Log.e(className, exception.message) }

    fun removeListItems() = listItems.clear()
}
