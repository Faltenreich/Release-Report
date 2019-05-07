package com.faltenreich.releaseradar.data.viewmodel

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.faltenreich.releaseradar.data.enum.MediaType
import com.faltenreich.releaseradar.data.model.Release
import com.faltenreich.releaseradar.data.repository.ReleaseRepository
import org.threeten.bp.DayOfWeek
import org.threeten.bp.LocalDate

class SpotlightViewModel : ViewModel() {

    private val weeklyReleasesLiveData = MutableLiveData<List<Release>>()
    private val favoriteReleasesLiveData = MutableLiveData<List<Release>>()
    private val recentReleasesLiveData = MutableLiveData<List<Release>>()
    private val today = LocalDate.now()

    private var releasesOfWeek: List<Release>?
        get() = weeklyReleasesLiveData.value
        set(value) = weeklyReleasesLiveData.postValue(value)

    private var favoriteReleases: List<Release>?
        get() = favoriteReleasesLiveData.value
        set(value) = favoriteReleasesLiveData.postValue(value)

    private var recentReleases: List<Release>?
        get() = recentReleasesLiveData.value
        set(value) = recentReleasesLiveData.postValue(value)

    fun observeWeeklyReleases(type: MediaType, owner: LifecycleOwner, onObserve: (List<Release>) -> Unit) {
        weeklyReleasesLiveData.observe(owner, Observer { releases -> onObserve(releases) })
        ReleaseRepository.getBetween(today.with(DayOfWeek.MONDAY), today.with(DayOfWeek.SUNDAY), type, CHUNK_SIZE) { releases -> releasesOfWeek = releases }
    }

    fun observeFavoriteReleases(type: MediaType, owner: LifecycleOwner, onObserve: (List<Release>) -> Unit) {
        favoriteReleasesLiveData.observe(owner, Observer { releases -> onObserve(releases) })
        ReleaseRepository.getFavorites(type, today) { releases -> favoriteReleases = releases.sortedBy(Release::releasedAt).take(CHUNK_SIZE) }
    }

    fun observeRecentReleases(type: MediaType, owner: LifecycleOwner, onObserve: (List<Release>) -> Unit) {
        recentReleasesLiveData.observe(owner, Observer { releases -> onObserve(releases) })
        val endAt = today.minusWeeks(1).with(DayOfWeek.SUNDAY)
        val startAt = endAt.minusMonths(1)
        ReleaseRepository.getBetween(startAt, endAt, type, CHUNK_SIZE) { releases -> recentReleases = releases }
    }

    companion object {
        private const val CHUNK_SIZE = 10
    }
}
