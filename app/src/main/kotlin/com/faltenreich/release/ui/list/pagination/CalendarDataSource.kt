package com.faltenreich.release.ui.list.pagination

import androidx.paging.PageKeyedDataSource
import com.faltenreich.release.data.repository.ReleaseRepository
import com.faltenreich.release.extension.LocalDateProgression
import com.faltenreich.release.extension.atEndOfWeek
import com.faltenreich.release.extension.atStartOfWeek
import com.faltenreich.release.extension.isTrue
import com.faltenreich.release.ui.list.item.CalendarDayItem
import com.faltenreich.release.ui.list.item.CalendarItem
import com.faltenreich.release.ui.list.item.CalendarMonthItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.threeten.bp.YearMonth

private typealias CalendarKey = YearMonth

class CalendarDataSource(private val startAt: YearMonth) : PageKeyedDataSource<CalendarKey, CalendarItem>() {

    override fun loadInitial(params: LoadInitialParams<CalendarKey>, callback: LoadInitialCallback<CalendarKey, CalendarItem>) {
        load(startAt, params.requestedLoadSize, true, object : LoadCallback<CalendarKey, CalendarItem>() {
            override fun onResult(data: MutableList<CalendarItem>, adjacentPageKey: CalendarKey?) {
                callback.onResult(data, startAt.minusMonths(1), adjacentPageKey)
            }
        })
    }

    override fun loadBefore(params: LoadParams<CalendarKey>, callback: LoadCallback<CalendarKey, CalendarItem>) {
        load(params.key, params.requestedLoadSize, false, callback)
    }

    override fun loadAfter(params: LoadParams<CalendarKey>, callback: LoadCallback<CalendarKey, CalendarItem>) {
        load(params.key, params.requestedLoadSize, true, callback)
    }

    private fun load(yearMonth: CalendarKey, pageSize: Int, descending: Boolean, callback: LoadCallback<CalendarKey, CalendarItem>) {
        val progression = if (descending) (0L until pageSize) else (-pageSize + 1L..0L)
        val yearMonths = progression.map { page -> yearMonth.plusMonths(page) }
        val (firstMonth, lastMonth) = yearMonths.first() to yearMonths.last()
        val (start, end) = firstMonth.atDay(1) to lastMonth.atEndOfMonth()

        ReleaseRepository.getBetween(start, end) { releases ->
            GlobalScope.launch {
                val items = yearMonths.flatMap { yearMonth ->
                    val monthItem = CalendarMonthItem(start, yearMonth)
                    val startOfFirstWeek = yearMonth.atDay(1).atStartOfWeek
                    val endOfLastWeek = yearMonth.atEndOfMonth().atEndOfWeek
                    val dayItems = LocalDateProgression(startOfFirstWeek, endOfLastWeek).map { day ->
                        val releasesOfDay = releases.filter { release -> (release.releaseDate == day).isTrue }
                        // TODO: Improve performance by bulk requesting favorites
                        val hasFavorite = releasesOfDay.any { release -> release.isFavorite }
                        CalendarDayItem(day, yearMonth, hasFavorite, releasesOfDay.size)
                    }
                    listOf(monthItem).plus(dayItems)
                }
                val adjacent = if (descending) lastMonth.plusMonths(1) else firstMonth.minusMonths(1)
                GlobalScope.launch(Dispatchers.Main) { callback.onResult(items, adjacent) }
            }
        }
    }
}