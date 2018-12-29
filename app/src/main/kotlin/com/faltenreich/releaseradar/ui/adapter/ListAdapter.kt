package com.faltenreich.releaseradar.ui.adapter

interface ListAdapter <MODEL : Any> {

    val items: ArrayList<MODEL>

    fun getItemCount(): Int = this.items.size

    fun getItem(position: Int): MODEL = this.items[position]

    fun add(item: MODEL) = this.items.add(item)

    fun addAt(position: Int, item: MODEL) = this.items.add(position, item)

    fun addAll(items: List<MODEL>) = this.items.addAll(items)

    fun remove(item: MODEL) = this.items.remove(item)

    fun removeAt(position: Int) = this.items.removeAt(position)

    fun removeAll() = this.items.clear()

    fun clear() = removeAll()
}
