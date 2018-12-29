package com.faltenreich.releaseradar.data.paging

import androidx.paging.ItemKeyedDataSource
import com.faltenreich.releaseradar.data.asString
import com.faltenreich.releaseradar.data.dao.Query
import com.faltenreich.releaseradar.data.model.Release
import com.faltenreich.releaseradar.data.repository.ReleaseRepository
import org.threeten.bp.LocalDate

object ReleaseDataSource : ItemKeyedDataSource<String, Release>() {

    private val orderBy by lazy { "releasedAt" }
    private val startAt by lazy { LocalDate.now().asString }

    override fun getKey(item: Release): String = item.id ?: ""

    override fun loadInitial(params: LoadInitialParams<String>, callback: LoadInitialCallback<Release>) {
        ReleaseRepository.getAll(
            Query(
                orderBy = orderBy,
                limit = params.requestedLoadSize,
                startAt = startAt to null
            ), onSuccess = { releases ->
                callback.onResult(releases)
            }, onError = {
                callback.onResult(listOf())
            })
    }

    // TODO
    override fun loadBefore(params: LoadParams<String>, callback: LoadCallback<Release>) = Unit

    // FIXME: Ignores params.key
    override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<Release>) {
        ReleaseRepository.getAll(
            Query(
                orderBy = orderBy,
                limit = params.requestedLoadSize,
                startAt = startAt to params.key
            ), onSuccess = { releases ->
                callback.onResult(releases)
            }, onError = {
                callback.onResult(listOf())
            })
    }
}