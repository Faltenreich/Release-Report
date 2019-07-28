package com.faltenreich.release.ui.viewpager

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.viewpager.widget.PagerAdapter
import com.faltenreich.release.ui.logic.adapter.MutableCollectionAdapter

abstract class ViewPagerAdapter<ITEM : Any>(
    @LayoutRes private val layoutResId: Int
) : PagerAdapter(), MutableCollectionAdapter<ITEM> {

    override val listItems = ArrayList<ITEM>()

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val context = container.context
        val view = LayoutInflater.from(context).inflate(layoutResId, container, false)
        container.addView(view)
        bind(getListItemAt(position)!!)
        return view
    }

    override fun getCount(): Int = getListItemCount()

    override fun isViewFromObject(view: View, `object`: Any): Boolean = view == `object`

    abstract fun bind(data: ITEM)
}