package com.faltenreich.releaseradar.data.viewmodel

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.faltenreich.releaseradar.data.enum.MediaType
import com.faltenreich.releaseradar.data.model.Release
import com.faltenreich.releaseradar.data.repository.ReleaseRepository
import com.faltenreich.releaseradar.extension.calendarWeek
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
        val filter = "${type.key}-${today.year}-${today.calendarWeek}-"
        weeklyReleasesLiveData.observe(owner, Observer { releases -> onObserve(releases) })
        TODO()
        /*
        ReleaseRepository.getAll(
            Query(
                orderBy = "indexForSpotlight",
                limitToLast = CHUNK_SIZE + 1,
                startAt = filter to null,
                endAt = "$filter\uf8ff" to null
            ), onSuccess = { releases ->
                releasesOfWeek = releases.reversed()
            }, onError = {
                releasesOfWeek = null
            })
            */
    }

    fun observeFavoriteReleases(type: MediaType, owner: LifecycleOwner, onObserve: (List<Release>) -> Unit) {
        favoriteReleasesLiveData.observe(owner, Observer { releases -> onObserve(releases) })
        ReleaseRepository.getFavorites(type, from = today) { releases ->
            val sorted = releases.sortedBy(Release::releasedAt).take(CHUNK_SIZE)
            favoriteReleases = sorted
        }
    }

    fun observeRecentReleases(type: MediaType, owner: LifecycleOwner, onObserve: (List<Release>) -> Unit) {
        val day = today.minusWeeks(1)
        val filter = "${type.key}-${day.year}-${day.calendarWeek}-"
        recentReleasesLiveData.observe(owner, Observer { releases -> onObserve(releases) })
        TODO()
        /*
        ReleaseRepository.getAll(
            Query(
                orderBy = "indexForSpotlight",
                limitToLast = CHUNK_SIZE,
                startAt = filter to null,
                endAt = "$filter\uf8ff" to null
            ), onSuccess = { releases ->
                recentReleases = releases.reversed()
            }, onError = {
                recentReleases = null
            })
        */
    }

    companion object {
        private const val CHUNK_SIZE = 10
    }
}
