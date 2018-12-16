package com.faltenreich.releaseradar.ui.adapter

import androidx.fragment.app.FragmentManager
import com.faltenreich.releaseradar.ui.fragment.SpotlightCategoryFragment

class ReleaseViewPagerAdapter(
        fragmentManager: FragmentManager?,
        content: List<Pair<String, SpotlightCategoryFragment>>
) : FragmentViewPagerAdapter(fragmentManager, content)