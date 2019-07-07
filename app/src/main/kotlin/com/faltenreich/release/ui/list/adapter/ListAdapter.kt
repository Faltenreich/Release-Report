package com.faltenreich.release.ui.list.adapter

interface ListAdapter <ITEM : Any> {
    val listItems: List<ITEM>

    fun getListItemCount(): Int = listItems.size

    fun getListItemAt(position: Int): ITEM? = listItems.getOrNull(position)
}
