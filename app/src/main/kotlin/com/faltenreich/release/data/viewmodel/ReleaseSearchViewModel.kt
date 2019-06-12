package com.faltenreich.release.data.viewmodel

import androidx.lifecycle.*
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.faltenreich.release.data.model.Release
import com.faltenreich.release.ui.list.paging.PagingDataFactory
import com.faltenreich.release.ui.list.paging.ReleaseSearchDataSource

class ReleaseSearchViewModel : ViewModel() {
    private val queryLiveData = MutableLiveData<String?>()
    private lateinit var releaseLiveData: LiveData<PagedList<Release>>

    val releases: PagedList<Release>?
        get() = releaseLiveData.value

    var query: String?
        get() = queryLiveData.value
        set(value) = queryLiveData.postValue(value)

    fun observe(owner: LifecycleOwner, onObserve: (PagedList<Release>) -> Unit, onInitialLoad: ((List<Release>) -> Unit)? = null) {
        queryLiveData.observe(owner, Observer {
            val dataSource = ReleaseSearchDataSource(query, onInitialLoad)
            val dataFactory = PagingDataFactory(dataSource, PAGE_SIZE)
            releaseLiveData = LivePagedListBuilder(dataFactory, dataFactory.config).build()
            releaseLiveData.observe(owner, Observer { releases -> onObserve(releases) })
        })
    }

    companion object {
        private const val PAGE_SIZE = 30
    }
}
