package com.faltenreich.release.framework.android.viewpager

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import java.lang.ref.WeakReference

abstract class ViewPager2FragmentAdapter(
    host: Fragment,
    private val contextReference: WeakReference<Context?> = WeakReference(host.context)
) : FragmentStateAdapter(host), PageTitleProvider {

    val context: Context?
        get() = contextReference.get()
}