package com.faltenreich.release.ui.fragment

import android.os.Bundle
import android.view.View
import com.faltenreich.release.R
import com.faltenreich.release.data.viewmodel.CalendarViewModel
import com.faltenreich.release.data.viewmodel.MainViewModel
import com.faltenreich.release.ui.activity.BaseActivity
import com.faltenreich.release.ui.list.adapter.CalendarListAdapter
import com.faltenreich.release.ui.list.layoutmanager.CalendarLayoutManager
import com.faltenreich.release.ui.view.TintAction
import kotlinx.android.synthetic.main.fragment_release_list.*
import org.threeten.bp.LocalDate

class CalendarFragment : BaseFragment(R.layout.fragment_calendar) {
    private val parentViewModel by lazy { (activity as BaseActivity).createViewModel(MainViewModel::class) }
    private val viewModel by lazy { createViewModel(CalendarViewModel::class) }

    private val listAdapter by lazy { context?.let { context -> CalendarListAdapter(context) } }
    private lateinit var listLayoutManager: CalendarLayoutManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        parentViewModel.tint = TintAction(R.color.colorPrimary)
        initList()
        initData()
    }

    private fun initList() {
        context?.let { context ->
            listLayoutManager = CalendarLayoutManager(context, listAdapter)
            listView.layoutManager = listLayoutManager
            listView.adapter = listAdapter
        }
    }

    private fun initData() {
        viewModel.observeReleases(requireContext(), LocalDate.now(), this) { list -> listAdapter?.submitList(list) }
    }
}