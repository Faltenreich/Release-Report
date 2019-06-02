package com.faltenreich.release.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.faltenreich.release.R
import com.faltenreich.release.data.provider.Dateable
import com.faltenreich.release.data.viewmodel.MainViewModel
import com.faltenreich.release.extension.printMonth
import com.faltenreich.release.ui.activity.BaseActivity
import com.faltenreich.release.ui.view.TintAction
import com.faltenreich.release.ui.viewpager.FragmentViewPagerAdapter
import kotlinx.android.synthetic.main.fragment_calendar_pager.*
import org.threeten.bp.LocalDate

class CalendarPagerFragment : BaseFragment(R.layout.fragment_calendar_pager) {
    private val parentViewModel by lazy { (activity as BaseActivity).createViewModel(MainViewModel::class) }
    private lateinit var viewPagerPages: List<Pair<String, Fragment>>
    private lateinit var viewPagerAdapter: FragmentViewPagerAdapter

    private val currentFragment: Fragment?
        get() = viewPagerAdapter.getItem(viewPager.currentItem).takeIf { fragment -> fragment.isAdded }

    private var currentDate: LocalDate = LocalDate.now()

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
        viewPagerPages =  (-1L..1L).map { index -> "" to CalendarFragment.newInstance(currentDate.plusMonths(index)) }
        viewPagerAdapter = FragmentViewPagerAdapter(childFragmentManager, viewPagerPages)
    }

    private fun initViewPager() {
        viewPager.adapter = viewPagerAdapter
        // FIXME: Do not listen until children are added
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) = Unit
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) = Unit
            override fun onPageSelected(position: Int) = invalidateDate()
        })
        viewPager.currentItem = 1
    }

    private fun invalidateDate() {
        val date = (currentFragment as? Dateable)?.date ?: currentDate
        dateLabel.text = date.printMonth(context)
    }
}