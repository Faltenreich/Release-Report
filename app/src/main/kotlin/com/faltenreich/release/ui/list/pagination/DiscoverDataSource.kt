package com.faltenreich.release.ui.list.pagination

import com.faltenreich.release.data.model.Release
import com.faltenreich.release.ui.list.item.ReleaseItem
import com.faltenreich.release.ui.list.item.ReleaseMoreItem
import com.faltenreich.release.ui.logic.provider.DateProvider
import org.threeten.bp.LocalDate

class DiscoverDataSource(startAt: LocalDate, afterLoadInitial: (Int) -> Unit) :ReleaseDataSource(startAt, afterLoadInitial) {
    override fun getListItemsForDate(date: LocalDate, releases: List<Release>): List<DateProvider> {
        val splitUpReleases = releases.size > MAXIMUM_RELEASES_PER_DAY
        return if (splitUpReleases) {
            val threshold = MAXIMUM_RELEASES_PER_DAY - 1
            val releaseItems = releases.subList(0, threshold).mapNotNull { release ->
                release.releaseDate?.let { date ->
                    ReleaseItem(
                        release,
                        date
                    )
                }
            }
            val moreReleases = releases.subList(threshold, releases.size)
            val moreItem = ReleaseMoreItem(date, moreReleases)
            releaseItems.plus(moreItem)

        } else {
            val releaseItems = releases.mapNotNull { release ->
                release.releaseDate?.let { date ->
                    ReleaseItem(
                        release,
                        date
                    )
                }
            }
            releaseItems
        }
    }

    companion object {
        private const val MAXIMUM_RELEASES_PER_DAY = 8
    }
}