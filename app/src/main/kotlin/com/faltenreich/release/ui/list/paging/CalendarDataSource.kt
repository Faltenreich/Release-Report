package com.faltenreich.release.ui.list.paging

import android.content.Context
import androidx.paging.PageKeyedDataSource
import com.faltenreich.release.data.repository.ReleaseRepository
import com.faltenreich.release.data.repository.RepositoryFactory
import com.faltenreich.release.extension.LocalDateProgression
import com.faltenreich.release.extension.atEndOfWeek
import com.faltenreich.release.extension.atStartOfWeek
import com.faltenreich.release.extension.isTrue
import com.faltenreich.release.ui.list.item.CalendarDayListItem
import com.faltenreich.release.ui.list.item.CalendarListItem
import com.faltenreich.release.ui.list.item.CalendarMonthListItem
import org.threeten.bp.YearMonth

class CalendarDataSource(
    private val context: Context,
    private var startAt: YearMonth
) : PageKeyedDataSource<YearMonth, CalendarListItem>() {
    private val releaseRepository = RepositoryFactory.repository<ReleaseRepository>()

    override fun loadInitial(params: LoadInitialParams<YearMonth>, callback: LoadInitialCallback<YearMonth, CalendarListItem>) {
        load(startAt, true, object : LoadCallback<YearMonth, CalendarListItem>() {
            override fun onResult(data: MutableList<CalendarListItem>, adjacentPageKey: YearMonth?) {
                callback.onResult(data, startAt.minusMonths(1), startAt.plusMonths(1))
            }
        })
    }

    override fun loadBefore(params: LoadParams<YearMonth>, callback: LoadCallback<YearMonth, CalendarListItem>) {
        load(params.key, false, callback)
    }

    override fun loadAfter(params: LoadParams<YearMonth>, callback: LoadCallback<YearMonth, CalendarListItem>) {
        load(params.key, true, callback)
    }

    private fun load(yearMonth: YearMonth, descending: Boolean, callback: LoadCallback<YearMonth, CalendarListItem>) {
        val startOfMonth = yearMonth.atDay(1)
        val endOfMonth = yearMonth.atEndOfMonth()
        val startOfFirstWeek = startOfMonth.atStartOfWeek(context)
        val endOfLastWeek = endOfMonth.atEndOfWeek(context)

        val monthItem = CalendarMonthListItem(startOfMonth, yearMonth)

        releaseRepository.getFavorites(startOfMonth, endOfMonth) { releases ->
            val dayItems = LocalDateProgression(startOfFirstWeek, endOfLastWeek).map { day ->
                val releasesOfToday = releases.filter { release -> (release.releaseDate == day).isTrue }
                CalendarDayListItem(day, yearMonth, releasesOfToday)
            }
            val items = listOf(monthItem).plus(dayItems)
            callback.onResult(items, if (descending) yearMonth.plusMonths(1) else yearMonth.minusMonths(1))
        }
    }
}