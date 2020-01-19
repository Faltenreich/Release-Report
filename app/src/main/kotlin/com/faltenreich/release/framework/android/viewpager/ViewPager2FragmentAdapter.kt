package com.faltenreich.release.framework.android.viewpager

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPager2FragmentAdapter(
    host: Fragment,
    val children: MutableList<Pair<String, Fragment>> = mutableListOf()
) : FragmentStateAdapter(host), PageTitleProvider {

    override fun getItemCount(): Int = children.count()

    override fun createFragment(position: Int): Fragment = children[position].second

    override fun getPageTitle(position: Int): String = children[position].first
}
