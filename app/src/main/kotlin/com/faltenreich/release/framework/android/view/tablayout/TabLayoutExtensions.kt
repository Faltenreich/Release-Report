package com.faltenreich.release.framework.android.view.tablayout

import androidx.viewpager2.widget.ViewPager2
import com.faltenreich.release.framework.android.view.viewpager.PageTitleProvider
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

fun TabLayout.setupWithViewPager2(viewPager: ViewPager2) {
    val pageTitleProvider = viewPager.adapter as PageTitleProvider

    val strategy = TabLayoutMediator.TabConfigurationStrategy { tab, position ->
        tab.text = pageTitleProvider.getPageTitle(position)
    }

    val mediator = TabLayoutMediator(this, viewPager, strategy)

    mediator.attach()
}
