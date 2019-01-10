package com.faltenreich.releaseradar.ui.list.paging

import com.faltenreich.releaseradar.data.dao.Query
import com.faltenreich.releaseradar.data.model.Release
import com.faltenreich.releaseradar.data.repository.ReleaseRepository
import com.faltenreich.releaseradar.extension.asString
import com.faltenreich.releaseradar.extension.isTrue
import com.faltenreich.releaseradar.ui.list.adapter.ReleaseListItem
import org.threeten.bp.LocalDate

class ReleaseDataSource(private val onInitialLoad: (() -> Unit)? = null) : PagingDataSource<ReleaseListItem>() {
    private var startAtDate: LocalDate = LocalDate.now()
    private var startAtDateAsString: String = startAtDate.asString
    private var startAtId: String? = null
    private var endAtDateAsString: String = startAtDate.minusDays(1).asString
    private var endAtId: String? = null

    override fun getKey(item: ReleaseListItem): String = item.release?.id ?: ""

    override fun loadInitial(params: LoadInitialParams<String>, callback: LoadInitialCallback<ReleaseListItem>) = load(params.requestedLoadSize, true, object : LoadCallback<ReleaseListItem>() {
        override fun onResult(data: MutableList<ReleaseListItem>) {
            onInitialLoad?.invoke()
            callback.onResult(listOf(ReleaseListItem(startAtDate, null)).plus(data))
        }
    })

    override fun loadBefore(params: LoadParams<String>, callback: LoadCallback<ReleaseListItem>) = load(params.requestedLoadSize, false, callback)

    override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<ReleaseListItem>) = load(params.requestedLoadSize, true, callback)

    private fun load(requestedLoadSize: Int, descending: Boolean, callback: LoadCallback<ReleaseListItem>) {
        ReleaseRepository.getAll(
            Query(
                orderBy = "releasedAt",
                limitToFirst = if (descending) requestedLoadSize + 1 else null,
                limitToLast = if (!descending) requestedLoadSize + 1 else null,
                startAt = if (descending) startAtDateAsString to startAtId else null,
                endAt = if (!descending) endAtDateAsString to endAtId else null
            ), onSuccess = { releases ->
                releases.takeIf(List<Release>::isNotEmpty)?.let {
                    if (descending) {
                        releases.last().let { nextRelease ->
                            startAtId = nextRelease.id
                            startAtDateAsString = nextRelease.releasedAt ?: startAtDateAsString
                        }
                    } else {
                        releases.first().let { previousRelease ->
                            endAtId = previousRelease.id
                            endAtDateAsString = previousRelease.releasedAt ?: endAtDateAsString
                        }
                    }
                    val releaseListItems = mutableListOf<ReleaseListItem>()
                    releases.forEachIndexed { index, release ->
                        releaseListItems.add(ReleaseListItem(release.releaseDate, release))
                        // Add section header
                        releases.getOrNull(index + 1)?.let { nextRelease ->
                            if (nextRelease.releaseDate?.isAfter(release.releaseDate).isTrue) {
                                releaseListItems.add(ReleaseListItem(nextRelease.releaseDate, null))
                            }
                        }
                    }
                    callback.onResult(releaseListItems.dropLast(1))
                } ?: callback.onResult(listOf())
            }, onError = {
                callback.onResult(listOf())
            })
    }
}