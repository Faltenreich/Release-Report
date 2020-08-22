package com.faltenreich.release.domain.release.discover

import androidx.lifecycle.*
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.faltenreich.release.base.pagination.PagingDataFactory
import com.faltenreich.release.domain.date.DateProvider
import com.faltenreich.release.domain.release.list.ReleaseListDataSource
import com.faltenreich.release.domain.release.list.ReleaseProvider
import com.faltenreich.release.domain.release.search.SearchDataSource
import com.faltenreich.release.framework.android.architecture.LiveDataFix
import org.threeten.bp.LocalDate

class DiscoverViewModel : ViewModel() {

    private lateinit var releasesLiveData: LiveData<PagedList<DateProvider>?>
    val releases: List<DateProvider>
        get() = releasesLiveData.value ?: listOf()

    private val queryLiveData = LiveDataFix<String?>()
    var query: String?
        get() = queryLiveData.value
        set(value) = queryLiveData.postValue(value)

    private lateinit var queriedReleasesLiveData: LiveData<PagedList<ReleaseProvider>?>

    fun observeReleases(date: LocalDate, owner: LifecycleOwner, onObserve: (PagedList<DateProvider>?) -> Unit) {
        val dataSource = ReleaseListDataSource(viewModelScope, date)
        val dataFactory = PagingDataFactory(dataSource)
        releasesLiveData = LivePagedListBuilder(dataFactory, dataFactory.config).build()
        releasesLiveData.observe(owner, Observer { releases -> onObserve(releases) })
    }

    fun observeQuery(owner: LifecycleOwner, onObserve: (PagedList<ReleaseProvider>?) -> Unit) {
        queryLiveData.observe(owner, Observer { query ->
            query?.let {
                val dataSource = SearchDataSource(query, viewModelScope, {})
                val dataFactory = PagingDataFactory(dataSource)
                queriedReleasesLiveData = LivePagedListBuilder(dataFactory, dataFactory.config).build()
                queriedReleasesLiveData.observe(owner, Observer(onObserve))
            }
        })
    }
}
