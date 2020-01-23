package com.faltenreich.release.framework.android.viewpager

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

abstract class ViewPager2FragmentAdapter(
    host: Fragment,
    protected val context: Context? = host.context
) : FragmentStateAdapter(host), PageTitleProvider