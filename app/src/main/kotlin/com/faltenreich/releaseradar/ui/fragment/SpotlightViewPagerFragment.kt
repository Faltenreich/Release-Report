package com.faltenreich.releaseradar.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.faltenreich.releaseradar.R
import com.faltenreich.releaseradar.data.enum.MediaType
import com.faltenreich.releaseradar.extension.fadeBackgroundColorResource
import com.faltenreich.releaseradar.ui.viewpager.FragmentViewPagerAdapter
import kotlinx.android.synthetic.main.fragment_spotlight_viewpager.*

class SpotlightViewPagerFragment : BaseFragment(R.layout.fragment_spotlight_viewpager) {
    private val mediaTypes by lazy { MediaType.values() }
    private lateinit var viewPagerPages: List<Pair<String, Fragment>>
    private lateinit var viewPagerAdapter: FragmentViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initData()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewPager()
    }

    private fun initData() {
        viewPagerPages =  mediaTypes.map { mediaType -> getString(mediaType.pluralStringRes) to SpotlightFragment.newInstance(mediaType) }
        viewPagerAdapter = FragmentViewPagerAdapter(childFragmentManager, viewPagerPages)
    }

    private fun initViewPager() {
        viewPager.adapter = viewPagerAdapter
        tabLayout.setupWithViewPager(viewPager)
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) = Unit
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) = Unit
            override fun onPageSelected(position: Int) {
                setTint(mediaTypes[position])
            }
        })
        setTint(mediaTypes[0], false)
    }

    private fun setTint(type: MediaType, animated: Boolean = true) {
        if (animated) {
            viewPager.fadeBackgroundColorResource(type.colorDarkResId)
        } else {
            viewPager.setBackgroundResource(type.colorDarkResId)
        }
    }
}