package com.faltenreich.release.data.viewmodel

import android.content.Context
import androidx.lifecycle.*
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.faltenreich.release.ui.list.item.CalendarListItem
import com.faltenreich.release.ui.list.pagination.CalendarDataSource
import com.faltenreich.release.ui.list.pagination.PagingDataFactory
import org.threeten.bp.YearMonth

class CalendarViewModel : ViewModel() {
    private lateinit var releasesLiveData: LiveData<PagedList<CalendarListItem>>
    private val yearMonthLiveData = MutableLiveData<YearMonth>()

    var yearMonth: YearMonth?
        get() = yearMonthLiveData.value
        set(value) = yearMonthLiveData.postValue(value)

    fun observeReleases(context: Context, owner: LifecycleOwner, onObserve: (PagedList<CalendarListItem>) -> Unit) {
        val dataSource = CalendarDataSource(context, yearMonth ?: YearMonth.now())
        val dataFactory = PagingDataFactory(dataSource, PAGE_SIZE)
        releasesLiveData = LivePagedListBuilder(dataFactory, dataFactory.config).build()
        releasesLiveData.observe(owner, Observer { releases -> onObserve(releases) })
    }

    companion object {
        private const val PAGE_SIZE = 90 // Three months
    }
}