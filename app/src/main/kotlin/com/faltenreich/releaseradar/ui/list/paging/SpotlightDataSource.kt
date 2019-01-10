package com.faltenreich.releaseradar.ui.list.paging

import com.faltenreich.releaseradar.data.dao.Query
import com.faltenreich.releaseradar.data.model.Release
import com.faltenreich.releaseradar.data.repository.ReleaseRepository
import com.faltenreich.releaseradar.extension.asString
import org.threeten.bp.LocalDate

class SpotlightDataSource(startAtDate: LocalDate, endAtDate: LocalDate) : PagingDataSource<Release>() {
    private var startAtDateAsString: String = startAtDate.asString
    private var startAtId: String? = null
    private var endAtDateAsString: String = endAtDate.asString
    private var isFinished: Boolean = false

    override fun getKey(item: Release): String = item.id ?: ""

    override fun loadInitial(params: LoadInitialParams<String>, callback: LoadInitialCallback<Release>) = load(params.requestedLoadSize, callback)

    override fun loadBefore(params: LoadParams<String>, callback: LoadCallback<Release>) = Unit

    override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<Release>) = load(params.requestedLoadSize, callback)

    private fun load(requestedLoadSize: Int, callback: LoadCallback<Release>) {
        ReleaseRepository.getAll(
            Query(
                orderBy = "releasedAt",
                limitToFirst = requestedLoadSize,
                startAt = startAtDateAsString to startAtId,
                endAt = endAtDateAsString to null
            ), onSuccess = {
                if (isFinished) {
                    callback.onResult(listOf())
                } else {
                    it.takeIf(List<Release>::isNotEmpty)?.let { releases ->
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
                callback.onResult(listOf())
            })
    }
}