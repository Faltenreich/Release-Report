package com.faltenreich.releaseradar.ui.adapter

interface ListAdapter <ITEM : Any> {

    val items: ArrayList<ITEM>

    fun getItemCount(): Int = this.items.size

    fun getItem(position: Int): ITEM = this.items[position]

    fun add(item: ITEM) = this.items.add(item)

    fun addAt(position: Int, item: ITEM) = this.items.add(position, item)

    fun addAll(items: List<ITEM>) = this.items.addAll(items)

    fun remove(item: ITEM) = this.items.remove(item)

    fun removeAt(position: Int) = this.items.removeAt(position)

    fun removeAll() = this.items.clear()

    fun clear() = removeAll()
}
