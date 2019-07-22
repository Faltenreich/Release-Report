package com.faltenreich.release.data.viewmodel

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.faltenreich.release.R
import com.faltenreich.release.data.model.Release
import com.faltenreich.release.data.repository.ReleaseRepository
import com.faltenreich.release.data.repository.RepositoryFactory
import com.faltenreich.release.ui.list.item.*
import org.threeten.bp.DayOfWeek
import org.threeten.bp.LocalDate

class SpotlightViewModel : ViewModel() {
    private val releaseRepository = RepositoryFactory.repository<ReleaseRepository>()
    private val spotlightItemsLiveData = MutableLiveData<List<SpotlightItem>>()
    private val weeklyReleasesLiveData = MutableLiveData<List<Release>>()
    private val recentReleasesLiveData = MutableLiveData<List<Release>>()
    private val favoriteReleasesLiveData = MutableLiveData<List<Release>>()

    var spotlightItems: List<SpotlightItem>?
        get() = spotlightItemsLiveData.value
        set(value) = spotlightItemsLiveData.postValue(value)

    private var weeklyReleases: List<Release>?
        get() = weeklyReleasesLiveData.value
        set(value) = weeklyReleasesLiveData.postValue(value)

    private var recentReleases: List<Release>?
        get() = recentReleasesLiveData.value
        set(value) = recentReleasesLiveData.postValue(value)

    private var favoriteReleases: List<Release>?
        get() = favoriteReleasesLiveData.value
        set(value) = favoriteReleasesLiveData.postValue(value)

    fun observeData(owner: LifecycleOwner, pageSize: Int, onObserve: (List<SpotlightItem>) -> Unit) {
        spotlightItemsLiveData.observe(owner, Observer { data -> onObserve(data) })
        weeklyReleasesLiveData.observe(owner, Observer { refresh() })
        recentReleasesLiveData.observe(owner, Observer { refresh() })
        favoriteReleasesLiveData.observe(owner, Observer { refresh() })

        val today = LocalDate.now()
        val endAt = today.minusWeeks(1).with(DayOfWeek.SUNDAY)
        val startAt = endAt.minusMonths(1)

        releaseRepository.getBetween(today.with(DayOfWeek.MONDAY), today.with(DayOfWeek.SUNDAY), pageSize + 1) { releases -> weeklyReleases = releases }
        releaseRepository.getFavorites(today, pageSize) { releases -> favoriteReleases = releases }
        releaseRepository.getBetween(startAt, endAt, pageSize) { releases -> recentReleases = releases }
    }

    private fun refresh() {
        val items = mutableListOf<SpotlightItem>()
        weeklyReleases?.firstOrNull()?.let { release ->
            items.add(SpotlightPromoItem(release))
        }
        favoriteReleases?.takeIf(List<Any>::isNotEmpty)?.let { releases ->
            items.add(SpotlightHeaderItem(R.string.for_you))
            items.addAll(releases.map { release -> SpotlightReleaseItem(release, DateStyle.FULL) })
        }
        weeklyReleases?.drop(1)?.takeIf(List<Any>::isNotEmpty)?.let { releases ->
            items.add(SpotlightHeaderItem(R.string.this_week))
            items.addAll(releases.map { release -> SpotlightReleaseItem(release, DateStyle.SHORT) })
        }
        recentReleases?.takeIf(List<Any>::isNotEmpty)?.let { releases ->
            items.add(SpotlightHeaderItem(R.string.recently))
            items.addAll(releases.map { release -> SpotlightReleaseItem(release, DateStyle.FULL) })
        }
        spotlightItems = items.toList()
    }
}
