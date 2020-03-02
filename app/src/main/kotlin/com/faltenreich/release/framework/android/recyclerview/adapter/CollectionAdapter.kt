package com.faltenreich.release.framework.android.recyclerview.adapter

interface CollectionAdapter <ITEM : Any> {
    val listItems: List<ITEM>
    fun getListItemCount(): Int = listItems.size
    fun getListItemAt(position: Int): ITEM? = listItems.getOrNull(position)
}
