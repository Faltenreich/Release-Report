package com.faltenreich.release.domain.release.discover

import androidx.lifecycle.*
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.faltenreich.release.base.pagination.PagingDataFactory
import com.faltenreich.release.domain.date.DateProvider
import org.threeten.bp.LocalDate

class DiscoverViewModel : ViewModel() {
    private val dateLiveData = MutableLiveData<LocalDate>()
    private lateinit var releasesLiveData: LiveData<PagedList<DateProvider>>

    var date: LocalDate?
        get() = dateLiveData.value
        set(value) = dateLiveData.postValue(value)

    val releases: List<DateProvider>
        get() = releasesLiveData.value ?: listOf()

    fun observeReleases(date: LocalDate, owner: LifecycleOwner, onObserve: (PagedList<DateProvider>) -> Unit) {
        val dataSource = DiscoverDataSource(date)
        val dataFactory = PagingDataFactory(dataSource)
        releasesLiveData = LivePagedListBuilder(dataFactory, dataFactory.config).build()
        releasesLiveData.observe(owner, Observer { releases -> onObserve(releases) })
    }
}
