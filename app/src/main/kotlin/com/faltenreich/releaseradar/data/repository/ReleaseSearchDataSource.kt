package com.faltenreich.releaseradar.data.repository

import androidx.paging.ItemKeyedDataSource
import com.faltenreich.releaseradar.data.dao.Query
import com.faltenreich.releaseradar.data.model.Release
import com.faltenreich.releaseradar.ui.adapter.ReleaseListItem

class ReleaseSearchDataSource(private val query: String) : ItemKeyedDataSource<String, ReleaseListItem>() {
    private var startAtId: String? = null

    override fun getKey(item: ReleaseListItem): String = item.release?.id ?: ""

    override fun loadInitial(params: LoadInitialParams<String>, callback: LoadInitialCallback<ReleaseListItem>) = load(params.requestedLoadSize, callback)

    override fun loadBefore(params: LoadParams<String>, callback: LoadCallback<ReleaseListItem>) = Unit

    override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<ReleaseListItem>) = load(params.requestedLoadSize, callback)

    private fun load(requestedLoadSize: Int, callback: LoadCallback<ReleaseListItem>) {
        ReleaseRepository.getAll(
            Query(
                orderBy = "title",
                limitToFirst = requestedLoadSize,
                startAt = query to null,
                endAt = "\uf8ff" to null
            ), onSuccess = { releases ->
                releases.takeIf(List<Release>::isNotEmpty)?.let {
                    releases.last().let { nextRelease -> startAtId = nextRelease.id }
                    val releaseListItems = releases.map { release -> ReleaseListItem(release.releaseDate, release) }
                    callback.onResult(releaseListItems.dropLast(1))
                } ?: callback.onResult(listOf())
            }, onError = {
                callback.onResult(listOf())
            })
    }
}