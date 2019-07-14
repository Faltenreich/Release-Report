package com.faltenreich.release.data.viewmodel

import androidx.lifecycle.*
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.faltenreich.release.ui.list.item.DateItem
import com.faltenreich.release.ui.list.pagination.PagingDataFactory
import com.faltenreich.release.ui.list.pagination.ReleaseDataSource
import org.threeten.bp.LocalDate

class ReleaseListViewModel : ViewModel() {
    private val dateLiveData = MutableLiveData<LocalDate>()
    private lateinit var releasesLiveData: LiveData<PagedList<DateItem>>

    var date: LocalDate?
        get() = dateLiveData.value
        set(value) = dateLiveData.postValue(value)

    val releases: List<DateItem>
        get() = releasesLiveData.value ?: listOf()

    fun observeReleases(date: LocalDate, owner: LifecycleOwner, onObserve: (PagedList<DateItem>) -> Unit, onInitialLoad: ((Int) -> Unit)? = null) {
        val dataSource = ReleaseDataSource(startAt = date, onInitialLoad = onInitialLoad)
        val dataFactory = PagingDataFactory(dataSource, PAGE_SIZE)
        releasesLiveData = LivePagedListBuilder(dataFactory, dataFactory.config).build()
        releasesLiveData.observe(owner, Observer { releases -> onObserve(releases) })
    }

    companion object {
        private const val PAGE_SIZE = 3
    }
}
