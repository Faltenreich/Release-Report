package com.faltenreich.releaseradar.data.viewmodel

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.faltenreich.releaseradar.data.model.Release
import com.faltenreich.releaseradar.ui.list.paging.PagingDataFactory
import com.faltenreich.releaseradar.ui.list.paging.SpotlightDataSource
import org.threeten.bp.DayOfWeek
import org.threeten.bp.LocalDate

class SpotlightViewModel : ViewModel() {
    private lateinit var releaseofWeekLiveData: LiveData<PagedList<Release>>

    var releaseofWeek: List<Release> = listOf()
        get() = releaseofWeekLiveData.value ?: listOf()

    fun observeReleasesOfWeek(owner: LifecycleOwner, onObserve: (PagedList<Release>) -> Unit) {
        val today = LocalDate.now()
        val monday = today.with(DayOfWeek.MONDAY)
        val sunday = today.with(DayOfWeek.SUNDAY)
        val dataSource = SpotlightDataSource(monday, sunday)
        val dataFactory = PagingDataFactory(dataSource)
        releaseofWeekLiveData = LivePagedListBuilder(dataFactory, dataFactory.config).build()
        releaseofWeekLiveData.observe(owner, Observer { releases -> onObserve(releases) })
    }
}
