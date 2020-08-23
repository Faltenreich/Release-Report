package com.faltenreich.release.domain.release

import com.faltenreich.release.domain.date.DateProvider
import com.faltenreich.release.domain.release.list.ReleaseDateItem
import com.faltenreich.release.framework.android.view.recyclerview.adapter.ListAdapter
import org.threeten.bp.LocalDate

class DateProviderNavigation(
    private val listAdapter: ListAdapter<out DateProvider>
) {

    fun getNearestPositionForDate(date: LocalDate): Int? {
        return getFirstPositionEqualDate(date)
            ?: getNearestPositionBeforeOrEqualDate(date)
            ?: getNearestPositionAfterDate(date)
    }

    private fun getFirstPositionEqualDate(date: LocalDate): Int? {
        return listAdapter.listItems
            .indexOfFirst { item -> item.date?.isEqual(date) == true }
            .takeIf { index -> index >= 0 }
    }

    private fun getNearestPositionBeforeOrEqualDate(date: LocalDate): Int? {
        return listAdapter.listItems
            .indexOfLast { item -> item is ReleaseDateItem && (item.date.isEqual(date) || item.date.isBefore(date)) }
            .takeIf { index -> index >= 0 }
    }

    private fun getNearestPositionAfterDate(date: LocalDate): Int? {
        return listAdapter.listItems
            .indexOfFirst { item -> item is ReleaseDateItem && item.date.isAfter(date) }
            .takeIf { index -> index >= 0 }
    }
}