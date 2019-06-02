package com.faltenreich.release.ui.fragment

import com.faltenreich.release.R
import com.faltenreich.release.data.viewmodel.CalendarViewModel

class CalendarFragment : BaseFragment(R.layout.fragment_calendar) {
    private val viewModel by lazy { createViewModel(CalendarViewModel::class) }


}