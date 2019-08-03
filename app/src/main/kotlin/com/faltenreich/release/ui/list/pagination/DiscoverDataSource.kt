package com.faltenreich.release.ui.list.pagination

import androidx.paging.PageKeyedDataSource
import com.faltenreich.release.data.model.Release
import com.faltenreich.release.data.repository.ReleaseRepository
import com.faltenreich.release.extension.isTrue
import com.faltenreich.release.ui.list.item.ReleaseDateItem
import com.faltenreich.release.ui.list.item.ReleaseItem
import com.faltenreich.release.ui.list.item.ReleaseMoreItem
import com.faltenreich.release.ui.logic.provider.DateProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDate

private typealias DiscoverKey = Int

class DiscoverDataSource(
    private var startAt: LocalDate,
    private val afterLoadInitial: (Int) -> Unit
) : PageKeyedDataSource<DiscoverKey, DateProvider>() {

    override fun loadInitial(params: LoadInitialParams<DiscoverKey>, callback: LoadInitialCallback<DiscoverKey, DateProvider>) {
        load(0, params.requestedLoadSize, true, object : LoadCallback<DiscoverKey, DateProvider>() {
            override fun onResult(data: MutableList<DateProvider>, adjacentPageKey: DiscoverKey?) {
                callback.onResult(data, 0, adjacentPageKey)
                afterLoadInitial(data.size)
            }
        })
    }

    override fun loadBefore(params: LoadParams<DiscoverKey>, callback: LoadCallback<DiscoverKey, DateProvider>) {
        load(params.key, params.requestedLoadSize, false, callback)
    }

    override fun loadAfter(params: LoadParams<DiscoverKey>, callback: LoadCallback<DiscoverKey, DateProvider>) {
        load(params.key, params.requestedLoadSize, true, callback)
    }

    private fun load(page: Int, pageSize: Int, descending: Boolean, callback: LoadCallback<DiscoverKey, DateProvider>) {
        val onResult = { releases: List<Release> -> onResponse(releases, page, callback) }
        if (descending) {
            ReleaseRepository.getAfter(startAt, page, pageSize, onResult)
        } else {
            ReleaseRepository.getBefore(startAt.minusDays(1), page, pageSize, onResult)
        }
    }

    private fun onResponse(releases: List<Release>, page: Int, callback: LoadCallback<DiscoverKey, DateProvider>) {
        GlobalScope.launch {
            val items = releases.groupBy(Release::releaseDate).flatMap { group ->
                group.key?.let { date ->
                    val dayItem = ReleaseDateItem(date)
                    val releasesOfDay = releases.filter { release -> release.releaseDate?.equals(date).isTrue }
                    if (releasesOfDay.isNotEmpty()) {
                        val splitUpReleases = releasesOfDay.size > MAXIMUM_RELEASES_PER_DAY
                        if (splitUpReleases) {
                            val threshold = MAXIMUM_RELEASES_PER_DAY - 1
                            val releaseItems = releasesOfDay.subList(0, threshold).mapNotNull { release ->
                                release.releaseDate?.let { date ->
                                    ReleaseItem(
                                        release,
                                        date
                                    )
                                }
                            }
                            val moreReleases = releasesOfDay.subList(threshold, releasesOfDay.size)
                            val moreItem = ReleaseMoreItem(date, moreReleases)
                            listOf(dayItem).plus(releaseItems).plus(moreItem)
                        } else {
                            val releaseItems = releasesOfDay.mapNotNull { release ->
                                release.releaseDate?.let { date ->
                                    ReleaseItem(
                                        release,
                                        date
                                    )
                                }
                            }
                            listOf(dayItem).plus(releaseItems)
                        }
                    } else {
                        listOf()
                    }
                } ?: listOf()
            }
            GlobalScope.launch(Dispatchers.Main) { callback.onResult(items, page + 1) }
        }
    }

    companion object {
        private const val MAXIMUM_RELEASES_PER_DAY = 8
    }
}