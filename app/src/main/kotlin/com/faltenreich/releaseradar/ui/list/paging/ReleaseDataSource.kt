package com.faltenreich.releaseradar.ui.list.paging

import android.util.Log
import com.faltenreich.releaseradar.data.model.Release
import com.faltenreich.releaseradar.data.repository.ReleaseRepository
import com.faltenreich.releaseradar.extension.asLocalDate
import com.faltenreich.releaseradar.extension.asString
import com.faltenreich.releaseradar.extension.isTrue
import com.faltenreich.releaseradar.tag
import com.faltenreich.releaseradar.ui.list.adapter.ReleaseListItem
import org.threeten.bp.LocalDate

class ReleaseDataSource(
    private var startAtDate: LocalDate,
    private val onInitialLoad: (() -> Unit)? = null
) : PagingDataSource<ReleaseListItem>() {
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
        val (startAt, endAt) = if (descending) startAtDateAsString.asLocalDate to null else null to endAtDateAsString.asLocalDate
        ReleaseRepository.getAll(startAt, endAt, requestedLoadSize, onSuccess = { releases ->
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
        }, onError = { exception ->
            Log.e(tag, exception?.message)
            callback.onResult(listOf())
        })
    }
}