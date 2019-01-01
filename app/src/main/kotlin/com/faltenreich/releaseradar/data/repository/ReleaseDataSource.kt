package com.faltenreich.releaseradar.data.repository

import androidx.paging.ItemKeyedDataSource
import com.faltenreich.releaseradar.data.asString
import com.faltenreich.releaseradar.data.dao.Query
import com.faltenreich.releaseradar.data.model.Release
import com.faltenreich.releaseradar.isTrue
import com.faltenreich.releaseradar.ui.adapter.ReleaseListItem
import org.threeten.bp.LocalDate

class ReleaseDataSource(private val onInitialLoad: (() -> Unit)? = null) : ItemKeyedDataSource<String, ReleaseListItem>() {
    private var startAtDate: LocalDate = LocalDate.now()
    private var startAtDateAsString: String = startAtDate.asString
    private var startAtId: String? = null

    override fun getKey(item: ReleaseListItem): String = item.release?.id ?: ""

    override fun loadInitial(params: LoadInitialParams<String>, callback: LoadInitialCallback<ReleaseListItem>) = load(params.requestedLoadSize, object : LoadCallback<ReleaseListItem>() {
        override fun onResult(data: MutableList<ReleaseListItem>) {
            onInitialLoad?.invoke()
            callback.onResult(listOf(ReleaseListItem(startAtDate, null)).plus(data))
        }
    })

    // TODO
    override fun loadBefore(params: LoadParams<String>, callback: LoadCallback<ReleaseListItem>) = Unit

    override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<ReleaseListItem>) = load(params.requestedLoadSize, callback)

    private fun load(requestedLoadSize: Int, callback: LoadCallback<ReleaseListItem>) {
        ReleaseRepository.getAll(
            Query(
                orderBy = "releasedAt",
                limit = requestedLoadSize + 1,
                startAt = startAtDateAsString to startAtId
            ), onSuccess = { releases ->
                releases.takeIf(List<Release>::isNotEmpty)?.let {
                    releases.last().let { nextRelease ->
                        startAtId = nextRelease.id
                        startAtDateAsString = nextRelease.releasedAtString ?: startAtDateAsString
                    }
                    val releaseListItems = mutableListOf<ReleaseListItem>()
                    releases.forEachIndexed { index, release ->
                        releaseListItems.add(ReleaseListItem(release.releaseDate, release))
                        // Add section header
                        releases.getOrNull(index + 1)?.let { nextRelease ->
                            if (nextRelease.releaseDate?.isAfter(release.releaseDate).isTrue()) {
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