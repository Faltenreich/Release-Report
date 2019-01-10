package com.faltenreich.releaseradar.ui.viewpager

import androidx.fragment.app.FragmentManager
import com.faltenreich.releaseradar.ui.fragment.SpotlightCategoryFragment
import com.faltenreich.releaseradar.ui.viewpager.FragmentViewPagerAdapter

class ReleaseViewPagerAdapter(
        fragmentManager: FragmentManager?,
        content: List<Pair<String, SpotlightCategoryFragment>>
) : FragmentViewPagerAdapter(fragmentManager, content)