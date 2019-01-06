package com.faltenreich.releaseradar.data.viewmodel

import androidx.lifecycle.*
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.faltenreich.releaseradar.data.repository.ReleaseDataFactory
import com.faltenreich.releaseradar.ui.adapter.ReleaseListItem

class ReleaseSearchViewModel : ViewModel() {
    private lateinit var releaseLiveData: LiveData<PagedList<ReleaseListItem>>
    private val queryLiveData: MutableLiveData<String?> = MutableLiveData()

    var releases: List<ReleaseListItem> = listOf()
        get() = releaseLiveData.value ?: listOf()

    var query: String?
        get() = queryLiveData.value
        set(value) =  queryLiveData.postValue(value)

    fun observeReleases(owner: LifecycleOwner, onObserve: (PagedList<ReleaseListItem>) -> Unit, onInitialLoad: (() -> Unit)? = null) {
        val dataFactory = ReleaseDataFactory(onInitialLoad)
        releaseLiveData = LivePagedListBuilder(dataFactory, dataFactory.config).build()
        releaseLiveData.observe(owner, Observer { releases -> onObserve(releases) })
    }
}
