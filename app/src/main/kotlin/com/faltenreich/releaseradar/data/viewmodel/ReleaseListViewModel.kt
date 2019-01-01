package com.faltenreich.releaseradar.data.viewmodel

import androidx.lifecycle.*
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.faltenreich.releaseradar.data.repository.ReleaseDataFactory
import com.faltenreich.releaseradar.ui.adapter.ReleaseListItem
import org.threeten.bp.LocalDate

class ReleaseListViewModel : ViewModel() {
    private val config by lazy { PagedList.Config.Builder().setInitialLoadSizeHint(PAGE_SIZE).setPageSize(PAGE_SIZE).build() }

    private lateinit var releaseLiveData: LiveData<PagedList<ReleaseListItem>>
    private val dateLiveData = MutableLiveData<LocalDate>()

    var releases: List<ReleaseListItem> = listOf()
        get() = releaseLiveData.value ?: listOf()

    var date: LocalDate?
        get() = dateLiveData.value
        set(value) = dateLiveData.postValue(value)

    fun observeReleases(owner: LifecycleOwner, onObserve: (PagedList<ReleaseListItem>) -> Unit, onInitialLoad: (() -> Unit)? = null) {
        releaseLiveData = LivePagedListBuilder(ReleaseDataFactory(onInitialLoad), config).build()
        releaseLiveData.observe(owner, Observer { releases -> onObserve(releases) })
    }

    companion object {
        private const val PAGE_SIZE = 60
    }
}
