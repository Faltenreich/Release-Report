package com.faltenreich.releaseradar.ui.fragment

import android.graphics.PointF
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.faltenreich.releaseradar.R
import com.faltenreich.releaseradar.data.enum.MediaType
import com.faltenreich.releaseradar.data.viewmodel.MainViewModel
import com.faltenreich.releaseradar.ui.activity.BaseActivity
import com.faltenreich.releaseradar.ui.view.TintAction
import com.faltenreich.releaseradar.ui.viewpager.FragmentViewPagerAdapter
import kotlinx.android.synthetic.main.fragment_spotlight_pager.*

class SpotlightPagerFragment : BaseFragment(R.layout.fragment_spotlight_pager) {
    private val parentViewModel by lazy { (activity as BaseActivity).createViewModel(MainViewModel::class) }
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
        tabLayout.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                parentViewModel.tint = TintAction(R.color.gray, PointF(event.rawX, event.rawY))
            }
            false
        }
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) = Unit
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) = Unit
            override fun onPageSelected(position: Int) {
                setMediaType(mediaTypes[position])
            }
        })
        setMediaType(mediaTypes[0])
    }

    private fun setMediaType(mediaType: MediaType) {
        parentViewModel.tint = TintAction(mediaType.colorResId)
    }
}