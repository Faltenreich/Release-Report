package com.faltenreich.release.framework.android.viewpager

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

open class ViewPager2FragmentAdapter(
    host: Fragment,
    val children: List<Pair<String, Fragment>>
) : FragmentStateAdapter(host), PageTitleProvider {

    override fun getItemCount(): Int = children.count()

    override fun createFragment(position: Int): Fragment = children[position].second

    override fun getPageTitle(position: Int): String = children[position].first
}
