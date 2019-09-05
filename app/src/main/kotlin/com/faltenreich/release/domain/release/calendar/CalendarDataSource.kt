package com.faltenreich.release.domain.release.calendar

import androidx.paging.PageKeyedDataSource
import com.faltenreich.release.base.date.LocalDateProgression
import com.faltenreich.release.base.date.atEndOfWeek
import com.faltenreich.release.base.date.atStartOfWeek
import org.threeten.bp.LocalDate
import org.threeten.bp.YearMonth

private typealias CalendarKey = YearMonth

class CalendarDataSource(
    private val startAt: YearMonth,
    private val onLoad: (start: LocalDate, end: LocalDate) -> Unit
) : PageKeyedDataSource<CalendarKey, CalendarItem>() {

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

        val items = yearMonths.flatMap { currentYearMonth ->
            val monthItem = CalendarMonthItem(start, currentYearMonth)
            val startOfFirstWeek = currentYearMonth.atDay(1).atStartOfWeek
            val endOfLastWeek = currentYearMonth.atEndOfMonth().atEndOfWeek
            val dayItems = LocalDateProgression(startOfFirstWeek, endOfLastWeek).map { day ->
                CalendarDayItem(day, currentYearMonth)
            }
            listOf(monthItem).plus(dayItems)
        }

        val adjacentPageKey = if (descending) lastMonth.plusMonths(1) else firstMonth.minusMonths(1)
        callback.onResult(items, adjacentPageKey)

        onLoad(start, end)
    }
}