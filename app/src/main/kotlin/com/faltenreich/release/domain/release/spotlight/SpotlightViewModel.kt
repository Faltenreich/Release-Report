package com.faltenreich.release.domain.release.spotlight

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.faltenreich.release.R
import com.faltenreich.release.base.date.Now
import com.faltenreich.release.base.date.atEndOfWeek
import com.faltenreich.release.base.date.atStartOfWeek
import com.faltenreich.release.data.repository.ReleaseRepository
import com.faltenreich.release.domain.release.list.ReleaseItem
import com.faltenreich.release.framework.android.architecture.LiveDataFix
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SpotlightViewModel : ViewModel() {

    private val spotlightItemsLiveData =
        LiveDataFix<List<SpotlightItem>?>()
    private var spotlightItems: List<SpotlightItem>?
        get() = spotlightItemsLiveData.value
        set(value) = spotlightItemsLiveData.postValue(value)

    fun observeData(owner: LifecycleOwner, onObserve: (List<SpotlightItem>?) -> Unit) {
        spotlightItemsLiveData.observe(owner, Observer(onObserve))
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch(Dispatchers.IO) {
            val today = Now.localDate()
            val (startOfWeek, endOfWeek) = today.atStartOfWeek to today.atEndOfWeek

            // Plus one for the promo
            val weeklyReleases = ReleaseRepository.getBetween(startOfWeek, endOfWeek, PAGE_SIZE + 1)
            val promo = weeklyReleases.firstOrNull()?.let { release -> SpotlightPromoItem(release) }
            val weekly = weeklyReleases.drop(1).takeIf(List<*>::isNotEmpty)?.let { releases ->
                SpotlightReleaseItem(
                    R.string.this_week,
                    releases.mapNotNull { release ->
                        release.releaseDate?.let { date -> ReleaseItem(release, date) }
                    }
                )
            }

            val subscriptionReleases =
                ReleaseRepository.getSubscriptions(Now.localDate(), PAGE_SIZE)
            val subscription = subscriptionReleases.takeIf(List<*>::isNotEmpty)?.let { releases ->
                SpotlightReleaseItem(
                    R.string.for_you,
                    releases.mapNotNull { release ->
                        release.releaseDate?.let { date -> ReleaseItem(release, date) }
                    }
                )
            }

            val lastMonth = today.minusMonths(1)
            val endOfLastWeek = startOfWeek.minusDays(1)
            val recentReleases = ReleaseRepository.getBetween(lastMonth, endOfLastWeek, PAGE_SIZE)
            val recent = recentReleases.takeIf(List<*>::isNotEmpty)?.let { releases ->
                SpotlightReleaseItem(
                    R.string.recently,
                    releases.mapNotNull { release ->
                        release.releaseDate?.let { date -> ReleaseItem(release, date) }
                    }
                )
            }

            spotlightItems = listOfNotNull(promo, subscription, weekly, recent)
        }
    }


    companion object {
        private const val PAGE_SIZE = 5
    }
}
