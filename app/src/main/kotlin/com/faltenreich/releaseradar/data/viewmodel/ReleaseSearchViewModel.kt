package com.faltenreich.releaseradar.data.viewmodel

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.faltenreich.releaseradar.data.repository.ReleaseDataFactory
import com.faltenreich.releaseradar.data.repository.ReleaseSearchDataSource
import com.faltenreich.releaseradar.ui.adapter.ReleaseListItem

class ReleaseSearchViewModel : ViewModel() {
    private lateinit var releaseLiveData: LiveData<PagedList<ReleaseListItem>>

    var releases: List<ReleaseListItem> = listOf()
        get() = releaseLiveData.value ?: listOf()

    fun observeReleases(query: String, owner: LifecycleOwner, onObserve: (PagedList<ReleaseListItem>) -> Unit) {
        val dataSource = ReleaseSearchDataSource(query)
        val dataFactory = ReleaseDataFactory(dataSource)
        releaseLiveData = LivePagedListBuilder(dataFactory, dataFactory.config).build()
        releaseLiveData.observe(owner, Observer { releases -> onObserve(releases) })
    }
}
