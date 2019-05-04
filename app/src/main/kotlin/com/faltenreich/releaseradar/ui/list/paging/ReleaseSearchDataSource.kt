package com.faltenreich.releaseradar.ui.list.paging

import com.faltenreich.releaseradar.data.model.Release
import com.faltenreich.releaseradar.extension.asString
import org.threeten.bp.LocalDate

class ReleaseSearchDataSource(private val query: String?, private val onInitialLoad: ((List<Release>) -> Unit)? = null) : PagingDataSource<Release>() {
    private var startAtDate: LocalDate = LocalDate.now()
    private var startAtDateAsString: String = startAtDate.asString
    private var startAtId: String? = null
    private var isFinished: Boolean = false

    override fun getKey(item: Release): String = item.id ?: ""

    override fun loadInitial(params: LoadInitialParams<String>, callback: LoadInitialCallback<Release>) = load(params.requestedLoadSize, callback, onInitialLoad)

    override fun loadBefore(params: LoadParams<String>, callback: LoadCallback<Release>) = Unit

    override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<Release>) = load(params.requestedLoadSize, callback)

    private fun load(requestedLoadSize: Int, callback: LoadCallback<Release>, onLoad: ((List<Release>) -> Unit)? = null) {
        TODO()
        /*
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
                        onLoad?.invoke(releases)
                        releases.takeIf(List<Release>::isNotEmpty)?.let {
                            isFinished = releases.size < requestedLoadSize
                            if (isFinished) {
                                startAtId = null
                                callback.onResult(releases)
                            } else {
                                releases.last().let { nextRelease ->
                                    startAtId = nextRelease.id
                                    startAtDateAsString = nextRelease.releasedAt ?: startAtDateAsString
                                }
                                callback.onResult(releases.dropLast(1))
                            }
                        } ?: callback.onResult(listOf())
                    }
                }, onError = {
                    onLoad?.invoke(listOf())
                    callback.onResult(listOf())
                })
        } ?: callback.onResult(listOf())
        */
    }
}