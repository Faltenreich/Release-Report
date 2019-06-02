package com.faltenreich.release.ui.fragment

import android.os.Bundle
import android.view.View
import com.faltenreich.release.R
import com.faltenreich.release.data.viewmodel.MainViewModel
import com.faltenreich.release.ui.activity.BaseActivity
import com.faltenreich.release.ui.view.TintAction

class CalendarPagerFragment : BaseFragment(R.layout.fragment_calendar_pager) {
    private val parentViewModel by lazy { (activity as BaseActivity).createViewModel(MainViewModel::class) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        parentViewModel.tint = TintAction(R.color.colorPrimary)
    }
}