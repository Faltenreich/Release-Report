package com.faltenreich.releaseradar.data.viewmodel

import androidx.lifecycle.*
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.faltenreich.releaseradar.ui.list.paging.PagingDataFactory
import com.faltenreich.releaseradar.ui.list.paging.ReleaseDataSource
import com.faltenreich.releaseradar.ui.list.adapter.ReleaseListItem
import org.threeten.bp.LocalDate

class ReleaseListViewModel : ViewModel() {
    private lateinit var releaseLiveData: LiveData<PagedList<ReleaseListItem>>
    private val dateLiveData = MutableLiveData<LocalDate>()

    var releases: List<ReleaseListItem> = listOf()
        get() = releaseLiveData.value ?: listOf()

    var date: LocalDate?
        get() = dateLiveData.value
        set(value) = dateLiveData.postValue(value)

    fun observeReleases(owner: LifecycleOwner, onObserve: (PagedList<ReleaseListItem>) -> Unit, onInitialLoad: (() -> Unit)? = null) {
        val dataSource = ReleaseDataSource(onInitialLoad)
        val dataFactory = PagingDataFactory(dataSource)
        releaseLiveData = LivePagedListBuilder(dataFactory, dataFactory.config).build()
        releaseLiveData.observe(owner, Observer { releases -> onObserve(releases) })
    }
}
