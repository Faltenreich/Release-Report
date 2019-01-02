package com.faltenreich.releaseradar.ui.adapter

interface ListAdapter <ITEM : Any> {
    val listItems: ArrayList<ITEM>

    fun getListItemCount(): Int = listItems.size

    fun getListItemAt(position: Int): ITEM = listItems[position]

    fun addListItem(item: ITEM) = listItems.add(item)

    fun addListItemAt(position: Int, item: ITEM) = listItems.add(position, item)

    fun addListItems(items: List<ITEM>) = this.listItems.addAll(items)

    fun removeListItem(item: ITEM) = listItems.remove(item)

    fun removeListItemAt(position: Int) = listItems.removeAt(position)

    fun removeListItems() = listItems.clear()
}
