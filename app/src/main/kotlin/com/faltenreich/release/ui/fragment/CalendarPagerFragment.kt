package com.faltenreich.release.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.faltenreich.release.R
import com.faltenreich.release.data.viewmodel.MainViewModel
import com.faltenreich.release.ui.activity.BaseActivity
import com.faltenreich.release.ui.view.TintAction
import com.faltenreich.release.ui.viewpager.FragmentViewPagerAdapter
import kotlinx.android.synthetic.main.fragment_calendar_pager.*

class CalendarPagerFragment : BaseFragment(R.layout.fragment_calendar_pager) {
    private val parentViewModel by lazy { (activity as BaseActivity).createViewModel(MainViewModel::class) }
    private lateinit var viewPagerPages: List<Pair<String, Fragment>>
    private lateinit var viewPagerAdapter: FragmentViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initData()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        parentViewModel.tint = TintAction(R.color.colorPrimary)
        initViewPager()
    }

    private fun initData() {
        viewPagerPages =  (0 until 3).map { "" to CalendarFragment() }
        viewPagerAdapter = FragmentViewPagerAdapter(childFragmentManager, viewPagerPages)
    }

    private fun initViewPager() {
        viewPager.adapter = viewPagerAdapter
    }
}