package com.faltenreich.release.domain.release.search

import androidx.lifecycle.*
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.faltenreich.release.base.pagination.PagingDataFactory
import com.faltenreich.release.domain.release.list.ReleaseProvider
import com.faltenreich.release.framework.androidx.LiveDataFix

class SearchViewModel : ViewModel() {

    private val queryLiveData = LiveDataFix<String?>()
    var query: String?
        get() = queryLiveData.value
        set(value) = queryLiveData.postValue(value)

    private lateinit var releaseLiveData: LiveData<PagedList<ReleaseProvider>?>
    val releases: PagedList<ReleaseProvider>?
        get() = releaseLiveData.value

    fun observeQuery(owner: LifecycleOwner, onObserve: (PagedList<ReleaseProvider>?) -> Unit, afterInitialLoad: (List<ReleaseProvider>?) -> Unit) {
        queryLiveData.observe(owner, Observer { query ->
            query?.let {
                val dataSource = SearchDataSource(query, viewModelScope, afterInitialLoad)
                val dataFactory = PagingDataFactory(dataSource)
                releaseLiveData = LivePagedListBuilder(dataFactory, dataFactory.config).build()
                releaseLiveData.observe(owner, Observer(onObserve))
            }
        })
    }
}
