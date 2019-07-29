package com.faltenreich.release.data.viewmodel

import androidx.lifecycle.*
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.faltenreich.release.ui.list.pagination.PagingDataFactory
import com.faltenreich.release.ui.list.pagination.SearchDataSource
import com.faltenreich.release.ui.logic.provider.ReleaseProvider

class SearchViewModel : ViewModel() {
    private val queryLiveData = MutableLiveData<String?>()
    private lateinit var releaseLiveData: LiveData<PagedList<ReleaseProvider>>

    val releases: PagedList<ReleaseProvider>?
        get() = releaseLiveData.value

    var query: String?
        get() = queryLiveData.value
        set(value) = queryLiveData.postValue(value)

    fun observe(owner: LifecycleOwner, onObserve: (PagedList<ReleaseProvider>) -> Unit) {
        queryLiveData.observe(owner, Observer { query ->
            val dataSource = SearchDataSource(query)
            val dataFactory = PagingDataFactory(dataSource, PAGE_SIZE)
            releaseLiveData = LivePagedListBuilder(dataFactory, dataFactory.config).build()
            releaseLiveData.observe(owner, Observer { releases -> onObserve(releases) })
        })
    }

    companion object {
        private const val PAGE_SIZE = 30
    }
}
