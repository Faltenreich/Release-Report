package com.faltenreich.release.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.faltenreich.release.R
import com.faltenreich.release.data.viewmodel.CalendarViewModel
import com.faltenreich.release.data.viewmodel.MainViewModel
import com.faltenreich.release.extension.*
import com.faltenreich.release.ui.activity.BaseActivity
import com.faltenreich.release.ui.list.adapter.CalendarListAdapter
import com.faltenreich.release.ui.list.item.CalendarDayListItem
import com.faltenreich.release.ui.list.layoutmanager.CalendarLayoutManager
import com.faltenreich.release.ui.view.TintAction
import kotlinx.android.synthetic.main.fragment_calendar.*
import kotlinx.android.synthetic.main.list_item_calendar_weekday.view.*
import org.threeten.bp.LocalDate
import org.threeten.bp.YearMonth
import org.threeten.bp.format.TextStyle
import kotlin.math.min

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

            listView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (isAdded) {
                        invalidateListHeader()
                    }
                }
            })

            val date = LocalDate.now()
            val startOfWeek = date.atStartOfWeek(context)
            val endOfWeek = date.atEndOfWeek(context)
            LocalDateProgression(startOfWeek, endOfWeek).map { day ->
                val dayOfWeek = day.dayOfWeek
                val view = LayoutInflater.from(context).inflate(R.layout.list_item_calendar_weekday, null)
                view.layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
                view.label.text = dayOfWeek.getDisplayName(TextStyle.SHORT, context.locale).toUpperCase()
                headerWeekDayLabels.addView(view)
            }
        }
    }

    private fun initData() {
        val date = LocalDate.now()
        val yearMonth = YearMonth.from(date)
        viewModel.observeReleases(requireContext(), yearMonth, this) { list ->
            listAdapter?.submitList(list)
        }
    }

    private fun invalidateListHeader() {
        val firstVisibleListItemPosition = listLayoutManager.findFirstVisibleItemPosition()
        val firstVisibleListItem = listAdapter?.currentList?.getOrNull(firstVisibleListItemPosition)
        val currentDate = firstVisibleListItem?.date ?: LocalDate.now()
        headerMonthLabel.text = currentDate?.printMonth(context)

        val secondVisibleListItem = listAdapter?.currentList?.getOrNull(firstVisibleListItemPosition + 8)
        val translateHeader = secondVisibleListItem !is CalendarDayListItem
        if (translateHeader) {
            val secondOffset = listLayoutManager.getChildAt(8)?.top ?: 0
            val top = secondOffset - header.height
            val translationY = min(top, 0)
            header.translationY = translationY.toFloat()
        } else {
            header.translationY = 0f
        }
    }
}