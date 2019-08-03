package com.faltenreich.release.ui.list.pagination

import com.faltenreich.release.data.model.Release
import com.faltenreich.release.ui.list.item.ReleaseItem
import com.faltenreich.release.ui.logic.provider.DateProvider
import org.threeten.bp.LocalDate

class ReleaseListDataSource(startAt: LocalDate) : ReleaseDataSource(startAt) {
    override fun getListItemsForDate(date: LocalDate, releases: List<Release>): List<DateProvider> {
        return releases.mapNotNull { release ->
            release.releaseDate?.let { date ->
                ReleaseItem(release, date)
            }
        }
    }
}