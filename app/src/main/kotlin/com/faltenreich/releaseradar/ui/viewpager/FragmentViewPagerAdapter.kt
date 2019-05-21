package com.faltenreich.releaseradar.ui.viewpager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class FragmentViewPagerAdapter(fragmentManager: FragmentManager, private val content: List<Pair<String, Fragment>>) : FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getCount(): Int = content.size
    override fun getItem(position: Int): Fragment = content[position].second
    override fun getPageTitle(position: Int): CharSequence? = content[position].first
}