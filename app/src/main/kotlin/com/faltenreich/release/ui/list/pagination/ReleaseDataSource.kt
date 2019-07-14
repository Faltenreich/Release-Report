package com.faltenreich.release.ui.list.pagination

import androidx.paging.PageKeyedDataSource
import com.faltenreich.release.data.model.Release
import com.faltenreich.release.data.repository.ReleaseRepository
import com.faltenreich.release.data.repository.RepositoryFactory
import com.faltenreich.release.extension.isTrue
import com.faltenreich.release.ui.list.item.ReleaseListItem
import org.threeten.bp.LocalDate

private typealias ReleaseKey = Int

class ReleaseDataSource(
    private var startAt: LocalDate,
    private val onInitialLoad: ((Int) -> Unit)? = null
) : PageKeyedDataSource<ReleaseKey, ReleaseListItem>() {
    private val releaseRepository = RepositoryFactory.repository<ReleaseRepository>()

    override fun loadInitial(params: LoadInitialParams<ReleaseKey>, callback: LoadInitialCallback<ReleaseKey, ReleaseListItem>) {
        load(0, params.requestedLoadSize, true, object : LoadCallback<ReleaseKey, ReleaseListItem>() {
            override fun onResult(data: MutableList<ReleaseListItem>, adjacentPageKey: ReleaseKey?) {
                onInitialLoad?.invoke(data.size)
                callback.onResult(data, 0, adjacentPageKey)
            }
        })
    }

    override fun loadBefore(params: LoadParams<ReleaseKey>, callback: LoadCallback<ReleaseKey, ReleaseListItem>) {
        load(params.key, params.requestedLoadSize, false, callback)
    }

    override fun loadAfter(params: LoadParams<ReleaseKey>, callback: LoadCallback<ReleaseKey, ReleaseListItem>) {
        load(params.key, params.requestedLoadSize, true, callback)
    }

    private fun load(page: Int, pageSize: Int, descending: Boolean, callback: LoadCallback<ReleaseKey, ReleaseListItem>) {
        val onResponse = { data: List<ReleaseListItem> -> callback.onResult(data, page + 1) }
        releaseRepository.getAll(startAt, descending, MIN_POPULARITY, page, pageSize) { releases ->

            releases.takeIf(List<Release>::isNotEmpty)?.let {
                val releaseListItems = mutableListOf<ReleaseListItem>()
                releases.forEachIndexed { index, release ->
                    releaseListItems.add(
                        ReleaseListItem(
                            release.releaseDate,
                            release
                        )
                    )
                    // Add section header
                    releases.getOrNull(index + 1)?.let { nextRelease ->
                        if (nextRelease.releaseDate?.isAfter(release.releaseDate).isTrue) {
                            releaseListItems.add(
                                ReleaseListItem(
                                    nextRelease.releaseDate,
                                    null
                                )
                            )
                        }
                    }
                }
                onResponse(releaseListItems)
            } ?: onResponse(listOf())
        }
    }

    companion object {
        private const val MIN_POPULARITY = 10f
    }
}