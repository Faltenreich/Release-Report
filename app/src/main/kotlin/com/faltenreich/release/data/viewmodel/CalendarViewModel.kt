package com.faltenreich.release.data.viewmodel

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.faltenreich.release.ui.list.item.CalendarListItem
import com.faltenreich.release.ui.list.paging.CalendarDataSource
import com.faltenreich.release.ui.list.paging.PagingDataFactory
import org.threeten.bp.LocalDate

class CalendarViewModel : ViewModel() {
    private lateinit var releasesLiveData: LiveData<PagedList<CalendarListItem>>

    val releases: List<CalendarListItem>
        get() = releasesLiveData.value ?: listOf()

    fun observeReleases(context: Context, date: LocalDate, owner: LifecycleOwner, onObserve: (PagedList<CalendarListItem>) -> Unit) {
        val dataSource = CalendarDataSource(context, date)
        val dataFactory = PagingDataFactory(dataSource, PAGE_SIZE)
        releasesLiveData = LivePagedListBuilder(dataFactory, dataFactory.config).build()
        releasesLiveData.observe(owner, Observer { releases -> onObserve(releases) })
    }

    companion object {
        private const val PAGE_SIZE = 3
    }
}
