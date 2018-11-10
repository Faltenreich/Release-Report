package com.faltenreich.releaseradar.ui.adapter

import androidx.fragment.app.FragmentManager
import com.faltenreich.releaseradar.ui.fragment.ReleaseListFragment

class ReleaseViewPagerAdapter(
        fragmentManager: FragmentManager?,
        content: List<Pair<String, ReleaseListFragment>>
) : FragmentViewPagerAdapter(fragmentManager, content)