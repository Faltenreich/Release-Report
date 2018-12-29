package com.faltenreich.releaseradar.data.viewmodel

import androidx.lifecycle.*
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.faltenreich.releaseradar.data.asString
import com.faltenreich.releaseradar.data.dao.Query
import com.faltenreich.releaseradar.data.model.Release
import com.faltenreich.releaseradar.data.paging.ReleaseDataFactory
import com.faltenreich.releaseradar.data.repository.ReleaseRepository
import org.threeten.bp.LocalDate
import java.util.concurrent.Executors

class ReleaseListViewModel : ViewModel() {
    private lateinit var releaseLiveData: LiveData<PagedList<Release>>
    private val dateLiveData = MutableLiveData<LocalDate>()

    var releases: List<Release> = listOf()
        get() = releaseLiveData.value ?: listOf()

    var date: LocalDate?
        get() = dateLiveData.value
        set(value) = dateLiveData.postValue(value)

    fun observeReleases(owner: LifecycleOwner, onObserve: (PagedList<Release>) -> Unit) {
        val config = PagedList.Config.Builder().setInitialLoadSizeHint(PAGE_SIZE).setPageSize(PAGE_SIZE).build()
        releaseLiveData = LivePagedListBuilder(ReleaseDataFactory, config).build()
        releaseLiveData.observe(owner, Observer { releases -> onObserve(releases) })
    }

    companion object {
        private const val PAGE_SIZE = 12
    }
}