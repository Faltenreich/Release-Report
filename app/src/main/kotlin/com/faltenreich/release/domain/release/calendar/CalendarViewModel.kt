package com.faltenreich.release.domain.release.calendar

import androidx.lifecycle.*
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.faltenreich.release.base.pagination.PagingDataFactory
import org.threeten.bp.YearMonth

class CalendarViewModel : ViewModel() {
    private lateinit var releasesLiveData: LiveData<PagedList<CalendarItem>>
    private val yearMonthLiveData = MutableLiveData<YearMonth>()

    var yearMonth: YearMonth?
        get() = yearMonthLiveData.value
        set(value) = yearMonthLiveData.postValue(value)

    fun observeReleases(
        yearMonth: YearMonth,
        owner: LifecycleOwner,
        onObserve: (PagedList<CalendarItem>) -> Unit
    ) {
        val dataSource = CalendarDataSource(yearMonth)
        val dataFactory = PagingDataFactory(dataSource, PAGE_SIZE_IN_MONTHS)
        releasesLiveData = LivePagedListBuilder(dataFactory, dataFactory.config).build()
        releasesLiveData.observe(owner, Observer { releases -> onObserve(releases) })
    }

    companion object {
        private const val PAGE_SIZE_IN_MONTHS = 3
    }
}