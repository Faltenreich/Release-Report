package com.faltenreich.release.domain.release.spotlight

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.faltenreich.release.R
import com.faltenreich.release.base.date.atEndOfWeek
import com.faltenreich.release.base.date.calendarWeek
import com.faltenreich.release.data.model.Release
import com.faltenreich.release.data.repository.ReleaseRepository
import com.faltenreich.release.domain.release.list.ReleaseItem
import org.threeten.bp.LocalDate

class SpotlightViewModel : ViewModel() {
    private val spotlightItemsLiveData = MutableLiveData<List<SpotlightItem>>()
    private val today by lazy { LocalDate.now() }

    private var spotlightItems: List<SpotlightItem>?
        get() = spotlightItemsLiveData.value
        set(value) = spotlightItemsLiveData.postValue(value)

    fun observeData(owner: LifecycleOwner, onObserve: (List<SpotlightItem>) -> Unit) {
        spotlightItemsLiveData.observe(owner, Observer(onObserve))

        val startAt = today.minusMonths(1)
        val endAt = today.atEndOfWeek

        ReleaseRepository.getBetween(startAt, endAt, PAGE_SIZE) { releases ->
            ReleaseRepository.getFavorites(LocalDate.now(), 5) { favorites ->
                setData(releases, favorites)
            }
        }
    }

    private fun setData(releases: List<Release>, favorites: List<Release>) {
        val items = mutableListOf<SpotlightItem>()
        val (weekly, recent) = releases.partition { release ->
            release.releaseDate?.calendarWeek == today.calendarWeek
        }

        addPromo(weekly, items)
        addFavorites(favorites, items)
        addWeekly(weekly, items)
        addRecent(recent, items)

        spotlightItems = items.toList()
    }

    private fun addPromo(from: List<Release>, to: MutableList<SpotlightItem>) {
        from.firstOrNull()?.let { release -> to.add(SpotlightPromoItem(release)) }
    }

    private fun addFavorites(from: List<Release>, to: MutableList<SpotlightItem>) {
        from.takeIf(List<*>::isNotEmpty)?.let { releases ->
            to.add(
                SpotlightReleaseItem(
                    R.string.for_you,
                    releases.mapNotNull { release ->
                        release.releaseDate?.let { date -> ReleaseItem(release, date) }
                    }
                )
            )
        }
    }

    private fun addWeekly(from: List<Release>, to: MutableList<SpotlightItem>) {
        from.drop(1).take(PARTITION_SIZE).takeIf(List<*>::isNotEmpty)?.let { releases ->
            to.add(
                SpotlightReleaseItem(
                    R.string.this_week,
                    releases.mapNotNull { release ->
                        release.releaseDate?.let { date -> ReleaseItem(release, date) }
                    }
                )
            )
        }
    }

    private fun addRecent(from: List<Release>, to: MutableList<SpotlightItem>) {
        from.take(PARTITION_SIZE).takeIf(List<*>::isNotEmpty)?.let { releases ->
            to.add(
                SpotlightReleaseItem(
                    R.string.recently,
                    releases.mapNotNull { release ->
                        release.releaseDate?.let { date -> ReleaseItem(release, date) }
                    }
                )
            )
        }
    }


    companion object {
        private const val PAGE_SIZE = 25
        private const val PARTITION_SIZE = 5
    }
}
