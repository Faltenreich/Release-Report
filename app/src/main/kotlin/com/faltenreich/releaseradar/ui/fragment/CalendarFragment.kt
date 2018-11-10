package com.faltenreich.releaseradar.ui.fragment
import android.os.Bundle
import android.view.View
import com.faltenreich.releaseradar.R
import kotlinx.android.synthetic.main.fragment_calendar.*
import java.util.*

class CalendarFragment : BaseFragment(R.layout.fragment_calendar) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLayout()
    }

    private fun initLayout() {
        calendarView.setDate(Date())
    }
}