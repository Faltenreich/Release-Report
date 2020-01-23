package com.faltenreich.release.framework.android.viewpager

import androidx.viewpager.widget.ViewPager

fun emptyOnPageChangeListener(): ViewPager.OnPageChangeListener {
    return object : ViewPager.OnPageChangeListener {
        override fun onPageScrollStateChanged(state: Int) = Unit
        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) = Unit
        override fun onPageSelected(position: Int) = Unit
    }
}