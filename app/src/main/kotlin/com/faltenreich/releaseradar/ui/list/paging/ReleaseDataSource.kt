package com.faltenreich.releaseradar.ui.list.paging

import android.util.Log
import com.faltenreich.releaseradar.data.model.Release
import com.faltenreich.releaseradar.data.repository.ReleaseRepository
import com.faltenreich.releaseradar.extension.isTrue
import com.faltenreich.releaseradar.tag
import com.faltenreich.releaseradar.ui.list.adapter.ReleaseListItem
import org.threeten.bp.LocalDate

class ReleaseDataSource(
    private var startAtDate: LocalDate,
    private var endAtDate: LocalDate = startAtDate,
    private val onInitialLoad: (() -> Unit)? = null
) : PagingDataSource<ReleaseListItem>() {

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
        val (startAt, endAt) = if (descending) startAtDate to null else null to endAtDate
        ReleaseRepository.getAll(startAt, endAt, requestedLoadSize, onSuccess = { releases ->
            releases.takeIf(List<Release>::isNotEmpty)?.let {
                if (descending) {
                    releases.last().let { nextRelease -> startAtDate = nextRelease.releaseDate ?: startAtDate }
                } else {
                    releases.first().let { previousRelease -> endAtDate = previousRelease.releaseDate ?: endAtDate }
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
                callback.onResult(releaseListItems)
            } ?: callback.onResult(listOf())
        }, onError = { exception ->
            Log.e(tag, exception?.message)
            callback.onResult(listOf())
        })
    }
}