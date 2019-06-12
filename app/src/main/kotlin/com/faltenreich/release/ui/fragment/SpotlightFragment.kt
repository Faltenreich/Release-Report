package com.faltenreich.release.ui.fragment

import android.graphics.PointF
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.faltenreich.release.R
import com.faltenreich.release.data.enum.ReleaseType
import com.faltenreich.release.data.viewmodel.MainViewModel
import com.faltenreich.release.ui.activity.BaseActivity
import com.faltenreich.release.ui.view.TintAction
import com.faltenreich.release.ui.viewpager.FragmentViewPagerAdapter
import kotlinx.android.synthetic.main.fragment_spotlight.*

class SpotlightFragment : BaseFragment(R.layout.fragment_spotlight) {
    private val parentViewModel by lazy { (activity as BaseActivity).createViewModel(MainViewModel::class) }
    private val mediaTypes by lazy { ReleaseType.values() }
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
        viewPagerPages =  mediaTypes.map { mediaType -> getString(mediaType.pluralStringRes) to SpotlightTypeFragment.newInstance(mediaType) }
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

    private fun setMediaType(releaseType: ReleaseType) {
        parentViewModel.tint = TintAction(releaseType.colorResId)
    }
}