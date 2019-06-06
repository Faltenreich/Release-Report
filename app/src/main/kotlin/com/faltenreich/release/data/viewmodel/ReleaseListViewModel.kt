package com.faltenreich.release.data.viewmodel

import androidx.lifecycle.*
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.faltenreich.release.ui.list.item.ReleaseListItem
import com.faltenreich.release.ui.list.paging.PagingDataFactory
import com.faltenreich.release.ui.list.paging.ReleaseDataSource
import org.threeten.bp.LocalDate

class ReleaseListViewModel : ViewModel() {
    private lateinit var releaseLiveData: LiveData<PagedList<ReleaseListItem>>
    private val dateLiveData = MutableLiveData<LocalDate>()

    val releases: List<ReleaseListItem>
        get() = releaseLiveData.value ?: listOf()

    var date: LocalDate?
        get() = dateLiveData.value
        set(value) = dateLiveData.postValue(value)

    fun observeReleases(date: LocalDate, owner: LifecycleOwner, onObserve: (PagedList<ReleaseListItem>) -> Unit, onInitialLoad: ((Int) -> Unit)? = null) {
        val dataSource = ReleaseDataSource(startAt = date, onInitialLoad = onInitialLoad)
        val dataFactory = PagingDataFactory(dataSource)
        releaseLiveData = LivePagedListBuilder(dataFactory, dataFactory.config).build()
        releaseLiveData.observe(owner, Observer { releases -> onObserve(releases) })
    }
}