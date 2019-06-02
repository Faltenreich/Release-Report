package com.faltenreich.release.data.viewmodel

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.faltenreich.release.data.model.Release
import com.faltenreich.release.data.repository.RepositoryFactory
import com.faltenreich.release.extension.atEndOfMonth
import com.faltenreich.release.extension.atStartOfMonth
import org.threeten.bp.LocalDate

class CalendarViewModel : ViewModel() {
    private val releaseRepository = RepositoryFactory.repositoryForReleases()

    private val dateLiveData = MutableLiveData<LocalDate>()
    private val releasesLiveData = MutableLiveData<List<Release>>()

    var date: LocalDate?
        get() = dateLiveData.value
        set(value) = dateLiveData.postValue(value)

    var releases: List<Release>?
        get() = releasesLiveData.value
        set(value) = releasesLiveData.postValue(value)

    fun observeDate(owner: LifecycleOwner, onObserve: (LocalDate) -> Unit) {
        dateLiveData.observe(owner, Observer { date ->
            onObserve(date)
            invalidateReleases()
        })
    }

    fun observeReleases(owner: LifecycleOwner, onObserve: (List<Release>) -> Unit) {
        releasesLiveData.observe(owner, Observer { releases -> onObserve(releases) })
    }

    private fun invalidateReleases() {
        date?.let { date ->
            releaseRepository.getFavorites(date.atStartOfMonth, date.atEndOfMonth) { releases ->
                this.releases = releases
            }
        }
    }
}
