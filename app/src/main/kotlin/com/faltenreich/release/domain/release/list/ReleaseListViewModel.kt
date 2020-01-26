package com.faltenreich.release.domain.release.list

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.faltenreich.release.base.pagination.PagingDataFactory
import com.faltenreich.release.domain.date.DateProvider
import org.threeten.bp.LocalDate

class ReleaseListViewModel : ViewModel() {
    private lateinit var releasesLiveData: LiveData<PagedList<DateProvider>?>

    val releases: List<DateProvider>
        get() = releasesLiveData.value ?: listOf()

    fun observeReleases(date: LocalDate, owner: LifecycleOwner, onObserve: (PagedList<DateProvider>?) -> Unit) {
        val dataSource = ReleaseListDataSource(date)
        val dataFactory = PagingDataFactory(dataSource)
        releasesLiveData = LivePagedListBuilder(dataFactory, dataFactory.config).build()
        releasesLiveData.observe(owner, Observer { releases -> onObserve(releases) })
    }
}
