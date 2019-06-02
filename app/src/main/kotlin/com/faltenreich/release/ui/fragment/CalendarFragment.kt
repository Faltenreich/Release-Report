package com.faltenreich.release.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.faltenreich.release.R
import com.faltenreich.release.data.viewmodel.CalendarViewModel
import com.faltenreich.release.ui.list.adapter.CalendarListAdapter
import com.faltenreich.release.ui.list.item.CalendarListItem
import kotlinx.android.synthetic.main.fragment_release_list.*
import org.threeten.bp.LocalDate

class CalendarFragment : BaseFragment(R.layout.fragment_calendar) {
    private val viewModel by lazy { createViewModel(CalendarViewModel::class) }

    private val listAdapter by lazy { context?.let { context -> CalendarListAdapter(context) } }
    private lateinit var listLayoutManager: GridLayoutManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()
        setData()
    }

    private fun initList() {
        context?.let { context ->
            listLayoutManager = GridLayoutManager(context, 7)
            listView.layoutManager = listLayoutManager
            listView.adapter = listAdapter
        }
    }

    private fun setData() {
        // TODO: Make month dynamic
        val date = LocalDate.now()
        val (dateStart, dateEnd) = date.withDayOfMonth(1) to date.withDayOfMonth(date.lengthOfMonth())

        val items = (0L until 31L).map { index ->
            CalendarListItem(LocalDate.now().minusDays(index), listOf())
        }
        listAdapter?.apply {
            removeListItems()
            addListItems(items)
            notifyDataSetChanged()
        }
    }
}