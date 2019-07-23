package com.faltenreich.release.data.viewmodel

import androidx.lifecycle.*
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.faltenreich.release.ui.list.pagination.PagingDataFactory
import com.faltenreich.release.ui.list.pagination.ReleaseListDataSource
import com.faltenreich.release.ui.logic.provider.DateProvider
import org.threeten.bp.LocalDate

class ReleaseListViewModel : ViewModel() {
    private val dateLiveData = MutableLiveData<LocalDate>()
    private lateinit var releasesLiveData: LiveData<PagedList<DateProvider>>

    var date: LocalDate?
        get() = dateLiveData.value
        set(value) = dateLiveData.postValue(value)

    val releases: List<DateProvider>
        get() = releasesLiveData.value ?: listOf()

    fun observeReleases(date: LocalDate, owner: LifecycleOwner, onObserve: (PagedList<DateProvider>) -> Unit) {
        val dataSource = ReleaseListDataSource(date)
        val dataFactory = PagingDataFactory(dataSource, PAGE_SIZE_IN_MONTHS)
        releasesLiveData = LivePagedListBuilder(dataFactory, dataFactory.config).build()
        releasesLiveData.observe(owner, Observer { releases -> onObserve(releases) })
    }

    companion object {
        private const val PAGE_SIZE_IN_MONTHS = 3
    }
}
