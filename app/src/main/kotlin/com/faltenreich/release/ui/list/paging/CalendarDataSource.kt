package com.faltenreich.release.ui.list.paging

import android.content.Context
import androidx.paging.PageKeyedDataSource
import com.faltenreich.release.data.repository.RepositoryFactory
import com.faltenreich.release.extension.*
import com.faltenreich.release.ui.list.item.CalendarDayListItem
import com.faltenreich.release.ui.list.item.CalendarListItem
import com.faltenreich.release.ui.list.item.CalendarMonthListItem
import com.faltenreich.release.ui.list.item.CalendarWeekDayListItem
import org.threeten.bp.LocalDate

class CalendarDataSource(
    private val context: Context,
    private var startAt: LocalDate
) : PageKeyedDataSource<LocalDate, CalendarListItem>() {
    private val releaseRepository = RepositoryFactory.repositoryForReleases()

    override fun loadInitial(params: LoadInitialParams<LocalDate>, callback: LoadInitialCallback<LocalDate, CalendarListItem>) {
        load(startAt, true, object : LoadCallback<LocalDate, CalendarListItem>() {
            override fun onResult(data: MutableList<CalendarListItem>, adjacentPageKey: LocalDate?) {
                callback.onResult(data, startAt.minusMonths(1), startAt.plusMonths(1))
            }
        })
    }

    override fun loadBefore(params: LoadParams<LocalDate>, callback: LoadCallback<LocalDate, CalendarListItem>) {
        load(params.key, false, callback)
    }

    override fun loadAfter(params: LoadParams<LocalDate>, callback: LoadCallback<LocalDate, CalendarListItem>) {
        load(params.key, true, callback)
    }

    private fun load(date: LocalDate, descending: Boolean, callback: LoadCallback<LocalDate, CalendarListItem>) {
        val onResponse = { data: List<CalendarListItem> -> callback.onResult(data, if (descending) date.plusMonths(1) else date.minusMonths(1)) }
        releaseRepository.getFavorites(date.atStartOfMonth, date.atEndOfMonth) { releases ->
            val startOfMonth = date.atStartOfMonth
            val startOfFirstWeek = startOfMonth.atStartOfWeek(context)
            val endOfFirstWeek = startOfFirstWeek.atEndOfWeek(context)
            val endOfLastWeek = date.atEndOfMonth.atEndOfWeek(context)
            val monthItem = CalendarMonthListItem(startOfMonth)
            val weekDayItems = LocalDateProgression(startOfFirstWeek, endOfFirstWeek).map { day -> CalendarWeekDayListItem(day) }
            val dayItems = LocalDateProgression(startOfFirstWeek, endOfLastWeek).map { day ->
                val releasesOfToday = releases.filter { release -> (release.releaseDate == day).isTrue }
                CalendarDayListItem(day, releasesOfToday, day.month == date.month)
            }
            val items = listOf(monthItem).plus(weekDayItems.plus(dayItems))
            onResponse(items)
        }
    }
}