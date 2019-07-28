package com.faltenreich.release.ui.logic.adapter

import android.util.Log
import com.faltenreich.release.extension.className

interface MutableCollectionAdapter <ITEM : Any> : CollectionAdapter<ITEM> {
    override val listItems: ArrayList<ITEM>
    fun addListItem(item: ITEM) = listItems.add(item)
    fun addListItemAt(position: Int, item: ITEM) = try { listItems.add(position, item) } catch (exception: IndexOutOfBoundsException) { Log.e(className, exception.message) }
    fun addListItems(items: List<ITEM>) = this.listItems.addAll(items)
    fun removeListItem(item: ITEM) = listItems.remove(item)
    fun removeListItemAt(position: Int) = try { listItems.removeAt(position) } catch (exception: IndexOutOfBoundsException) { Log.e(className, exception.message) }
    fun removeListItems() = listItems.clear()
}
