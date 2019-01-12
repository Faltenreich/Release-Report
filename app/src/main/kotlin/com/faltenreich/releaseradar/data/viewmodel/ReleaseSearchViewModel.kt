package com.faltenreich.releaseradar.data.viewmodel

import androidx.lifecycle.*
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.faltenreich.releaseradar.data.model.Release
import com.faltenreich.releaseradar.ui.list.paging.PagingDataFactory
import com.faltenreich.releaseradar.ui.list.paging.ReleaseSearchDataSource

class ReleaseSearchViewModel : ViewModel() {
    private val queryLiveData = MutableLiveData<String?>()
    private lateinit var releaseLiveData: LiveData<PagedList<Release>>

    var releases: PagedList<Release>? = null
        get() = releaseLiveData.value

    var query: String?
        get() = queryLiveData.value
        set(value) = queryLiveData.postValue(value)

    fun observe(owner: LifecycleOwner, onObserve: (PagedList<Release>) -> Unit) {
        queryLiveData.observe(owner, Observer {
            val dataSource = ReleaseSearchDataSource(query)
            val dataFactory = PagingDataFactory(dataSource)
            releaseLiveData = LivePagedListBuilder(dataFactory, dataFactory.config).build()
            releaseLiveData.observe(owner, Observer { releases -> onObserve(releases) })
        })
    }
}
