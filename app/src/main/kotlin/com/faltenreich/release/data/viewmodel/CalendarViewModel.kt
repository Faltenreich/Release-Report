package com.faltenreich.release.data.viewmodel

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.faltenreich.release.data.model.Release
import com.faltenreich.release.data.repository.RepositoryFactory

class CalendarViewModel : ViewModel() {
    private val releaseRepository = RepositoryFactory.repositoryForReleases()

    private val releasesLiveData = MutableLiveData<List<Release>>()

    private var releases: List<Release>?
        get() = releasesLiveData.value
        set(value) = releasesLiveData.postValue(value)

    fun observeFavoriteReleases(owner: LifecycleOwner, onObserve: (List<Release>) -> Unit) {
        releasesLiveData.observe(owner, Observer { releases -> onObserve(releases) })
        releaseRepository.getFavorites { releases -> this.releases = releases.sortedBy(Release::releasedAt) }
    }
}
