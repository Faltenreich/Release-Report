package com.faltenreich.release.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.faltenreich.release.R
import com.faltenreich.release.data.provider.Dateable
import com.faltenreich.release.data.viewmodel.CalendarViewModel
import com.faltenreich.release.extension.LocalDateProgression
import com.faltenreich.release.extension.atEndOfMonth
import com.faltenreich.release.extension.atStartOfMonth
import com.faltenreich.release.extension.isTrue
import com.faltenreich.release.ui.list.adapter.CalendarListAdapter
import com.faltenreich.release.ui.list.decoration.GridDividerDecoration
import com.faltenreich.release.ui.list.item.CalendarListItem
import kotlinx.android.synthetic.main.fragment_release_list.*
import org.threeten.bp.LocalDate

class CalendarFragment : BaseFragment(R.layout.fragment_calendar), Dateable {
    private val viewModel by lazy { createViewModel(CalendarViewModel::class) }
    private val givenDate: LocalDate? by lazy { arguments?.getSerializable(ARGUMENT_DATE) as? LocalDate }

    private val listAdapter by lazy { context?.let { context -> CalendarListAdapter(context) } }
    private lateinit var listLayoutManager: GridLayoutManager

    override val date: LocalDate?
        get() = viewModel.date

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()
        initData()
    }

    private fun initList() {
        context?.let { context ->
            listLayoutManager = GridLayoutManager(context, 7)
            listView.layoutManager = listLayoutManager
            listView.addItemDecoration(GridDividerDecoration(context))
            listView.adapter = listAdapter
        }
    }

    private fun initData() {
        viewModel.observeDate(this) { invalidateData() }
        viewModel.observeReleases(this) { invalidateData() }
        viewModel.date = givenDate
    }

    private fun invalidateData() {
        viewModel.date?.let { date ->
            val progression = LocalDateProgression(date.atStartOfMonth, date.atEndOfMonth)
            val items = progression.map { day ->
                val releases = viewModel.releases?.filter { release -> (release.releaseDate == day).isTrue }
                CalendarListItem(day, releases ?: listOf() )
            }
            listAdapter?.apply {
                removeListItems()
                addListItems(items)
                notifyDataSetChanged()
            }
        }
    }

    companion object {
        private const val ARGUMENT_DATE = "date"

        fun newInstance(date: LocalDate): CalendarFragment {
            val fragment = CalendarFragment()
            val arguments = Bundle()
            arguments.putSerializable(ARGUMENT_DATE, date)
            fragment.arguments = arguments
            return fragment
        }
    }
}