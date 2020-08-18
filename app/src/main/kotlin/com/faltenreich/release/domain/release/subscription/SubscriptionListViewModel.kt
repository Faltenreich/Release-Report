package com.faltenreich.release.domain.release.subscription

import androidx.lifecycle.*
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.faltenreich.release.base.pagination.PagingDataFactory
import com.faltenreich.release.domain.date.DateProvider
import com.faltenreich.release.domain.release.ReleaseDataSource
import org.threeten.bp.LocalDate

class SubscriptionListViewModel : ViewModel() {

    private lateinit var releasesLiveData: LiveData<PagedList<DateProvider>?>
    val releases: List<DateProvider>
        get() = releasesLiveData.value ?: listOf()

    fun observeReleases(date: LocalDate, owner: LifecycleOwner, onObserve: (PagedList<DateProvider>?) -> Unit) {
        val dataSource = ReleaseDataSource(viewModelScope, SubscriptionListDataLoader(), date)
        val dataFactory = PagingDataFactory(dataSource)
        releasesLiveData = LivePagedListBuilder(dataFactory, dataFactory.config).build()
        releasesLiveData.observe(owner, Observer { releases -> onObserve(releases) })
    }
}
