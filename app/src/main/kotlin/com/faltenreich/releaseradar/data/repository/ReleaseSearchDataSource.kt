package com.faltenreich.releaseradar.data.repository

import androidx.paging.ItemKeyedDataSource
import com.faltenreich.releaseradar.data.dao.Query
import com.faltenreich.releaseradar.data.model.Release
import com.faltenreich.releaseradar.extension.asString
import com.faltenreich.releaseradar.ui.adapter.ReleaseListItem
import org.threeten.bp.LocalDate

class ReleaseSearchDataSource(private val query: String?) : ItemKeyedDataSource<String, ReleaseListItem>() {
    private var startAtDate: LocalDate = LocalDate.now()
    private var startAtDateAsString: String = startAtDate.asString
    private var startAtId: String? = null
    private var isFinished: Boolean = false

    override fun getKey(item: ReleaseListItem): String = item.release?.id ?: ""

    override fun loadInitial(params: LoadInitialParams<String>, callback: LoadInitialCallback<ReleaseListItem>) = load(params.requestedLoadSize, callback)

    override fun loadBefore(params: LoadParams<String>, callback: LoadCallback<ReleaseListItem>) = Unit

    override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<ReleaseListItem>) = load(params.requestedLoadSize, callback)

    private fun load(requestedLoadSize: Int, callback: LoadCallback<ReleaseListItem>) {
        query?.let { query ->
            ReleaseRepository.getAll(
                Query(
                    orderBy = "releasedAt",
                    limitToFirst = requestedLoadSize,
                    startAt = startAtDateAsString to startAtId
                ), onSuccess = { result ->
                    if (isFinished) {
                        callback.onResult(listOf())
                    } else {
                        val releases = result.filter { release -> release.matches(query) }
                        releases.takeIf(List<Release>::isNotEmpty)?.let {
                            isFinished = releases.size < requestedLoadSize
                            val releaseListItems = releases.map { release -> ReleaseListItem(release.releaseDate, release) }
                            if (isFinished) {
                                startAtId = null
                                callback.onResult(releaseListItems)
                            } else {
                                releases.last().let { nextRelease ->
                                    startAtId = nextRelease.id
                                    startAtDateAsString = nextRelease.releasedAt ?: startAtDateAsString
                                }
                                callback.onResult(releaseListItems.dropLast(1))
                            }
                        } ?: callback.onResult(listOf())
                    }
                }, onError = {
                    callback.onResult(listOf())
                })
        } ?: callback.onResult(listOf())
    }
}