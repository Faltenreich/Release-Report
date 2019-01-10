package com.faltenreich.releaseradar.ui.viewpager

import androidx.fragment.app.FragmentManager
import com.faltenreich.releaseradar.ui.fragment.SpotlightFragment

class ReleaseViewPagerAdapter(
        fragmentManager: FragmentManager?,
        content: List<Pair<String, SpotlightFragment>>
) : FragmentViewPagerAdapter(fragmentManager, content)