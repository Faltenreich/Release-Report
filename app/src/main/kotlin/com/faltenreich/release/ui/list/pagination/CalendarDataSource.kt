package com.faltenreich.release.ui.list.pagination

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
    private val startAt: YearMonth
) : PageKeyedDataSource<YearMonth, CalendarListItem>() {
    private val releaseRepository = RepositoryFactory.repository<ReleaseRepository>()

    override fun loadInitial(params: LoadInitialParams<YearMonth>, callback: LoadInitialCallback<YearMonth, CalendarListItem>) {
        load(startAt, params.requestedLoadSize, true, object : LoadCallback<YearMonth, CalendarListItem>() {
            override fun onResult(data: MutableList<CalendarListItem>, adjacentPageKey: YearMonth?) {
                callback.onResult(data, startAt.minusMonths(1), adjacentPageKey)
            }
        })
    }

    override fun loadBefore(params: LoadParams<YearMonth>, callback: LoadCallback<YearMonth, CalendarListItem>) {
        load(params.key, params.requestedLoadSize, false, callback)
    }

    override fun loadAfter(params: LoadParams<YearMonth>, callback: LoadCallback<YearMonth, CalendarListItem>) {
        load(params.key, params.requestedLoadSize, true, callback)
    }

    private fun load(yearMonth: YearMonth, pageSize: Int, descending: Boolean, callback: LoadCallback<YearMonth, CalendarListItem>) {
        val progression = if (descending) (0L until pageSize) else (-pageSize + 1L .. 0L)
        val yearMonths = progression.map { page -> yearMonth.plusMonths(page) }

        val firstMonth = yearMonths.first()
        val lastMonth = yearMonths.last()

        val adjacent= if (descending) lastMonth.plusMonths(1) else firstMonth.minusMonths(1)

        val start = firstMonth.atDay(1)
        val end = lastMonth.atEndOfMonth()

        releaseRepository.getFavorites(start, end) { releases ->
            val items = yearMonths.flatMap { yearMonth ->
                val monthItem = CalendarMonthListItem(start, yearMonth)
                val startOfFirstWeek = yearMonth.atDay(1).atStartOfWeek(context)
                val endOfLastWeek = yearMonth.atEndOfMonth().atEndOfWeek(context)
                val dayItems = LocalDateProgression(startOfFirstWeek, endOfLastWeek).map { day ->
                    val releasesOfToday = releases.filter { release -> (release.releaseDate == day).isTrue }
                    CalendarDayListItem(day, yearMonth, releasesOfToday)
                }
                listOf(monthItem).plus(dayItems)
            }
            callback.onResult(items, adjacent)
        }
    }
}