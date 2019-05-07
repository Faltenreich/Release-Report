package com.faltenreich.releaseradar.ui.list.paging

import android.util.Log
import androidx.paging.PageKeyedDataSource
import com.faltenreich.releaseradar.data.model.Release
import com.faltenreich.releaseradar.data.repository.ReleaseRepository
import com.faltenreich.releaseradar.extension.isTrue
import com.faltenreich.releaseradar.tag
import com.faltenreich.releaseradar.ui.list.adapter.ReleaseListItem
import org.threeten.bp.LocalDate

class ReleaseDataSource(
    private var startAt: LocalDate,
    private val onInitialLoad: (() -> Unit)? = null
) : PageKeyedDataSource<Int, ReleaseListItem>() {

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, ReleaseListItem>) {
        load(0, params.requestedLoadSize, true, object : LoadCallback<Int, ReleaseListItem>() {
            override fun onResult(data: MutableList<ReleaseListItem>, adjacentPageKey: Int?) {
                onInitialLoad?.invoke()
                callback.onResult(listOf(ReleaseListItem(startAt, null)).plus(data), 0, 1)
            }
        })
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, ReleaseListItem>) {
        load(params.key, params.requestedLoadSize, false, callback)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, ReleaseListItem>) {
        load(params.key, params.requestedLoadSize, true, callback)
    }

    private fun load(page: Int, pageSize: Int, descending: Boolean, callback: LoadCallback<Int, ReleaseListItem>) {
        val onResponse = { data: List<ReleaseListItem> -> callback.onResult(data, page + 1) }
        ReleaseRepository.getAll(startAt, descending, MIN_POPULARITY, page, pageSize, onSuccess = { releases ->
            releases.takeIf(List<Release>::isNotEmpty)?.let {
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
                onResponse(releaseListItems)
            } ?: onResponse(listOf())
        }, onError = { exception ->
            Log.e(tag, exception?.message)
            onResponse(listOf())
        })
    }

    companion object {
        private const val MIN_POPULARITY = 10f
    }
}