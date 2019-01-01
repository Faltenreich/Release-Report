package com.faltenreich.releaseradar.data.repository

import androidx.paging.ItemKeyedDataSource
import com.faltenreich.releaseradar.data.asString
import com.faltenreich.releaseradar.data.dao.Query
import com.faltenreich.releaseradar.data.model.Release
import org.threeten.bp.LocalDate

class ReleaseDataSource(private val onInitialLoad: (() -> Unit)? = null) : ItemKeyedDataSource<String, Release>() {
    private var startAtDate: String = LocalDate.now().asString
    private var startAtId: String? = null

    override fun getKey(item: Release): String = item.id ?: ""

    override fun loadInitial(params: LoadInitialParams<String>, callback: LoadInitialCallback<Release>) = load(params.requestedLoadSize, object : LoadCallback<Release>() {
        override fun onResult(data: MutableList<Release>) {
            onInitialLoad?.invoke()
            callback.onResult(data)
        }
    })

    // TODO
    override fun loadBefore(params: LoadParams<String>, callback: LoadCallback<Release>) = Unit

    override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<Release>) = load(params.requestedLoadSize, callback)

    private fun load(requestedLoadSize: Int, callback: LoadCallback<Release>) {
        ReleaseRepository.getAll(
            Query(
                orderBy = "releasedAt",
                limit = requestedLoadSize + 1,
                startAt = startAtDate to startAtId
            ), onSuccess = { releases ->
                releases.takeIf(List<Release>::isNotEmpty)?.let {
                    releases.last().let { nextRelease ->
                        startAtId = nextRelease.id
                        startAtDate = nextRelease.releasedAtString ?: startAtDate
                    }
                    callback.onResult(releases.dropLast(1))
                } ?: callback.onResult(listOf())
            }, onError = {
                callback.onResult(listOf())
            })
    }
}