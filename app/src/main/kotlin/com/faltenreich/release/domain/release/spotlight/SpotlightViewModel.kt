package com.faltenreich.release.domain.release.spotlight

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.faltenreich.release.R
import com.faltenreich.release.base.date.atEndOfWeek
import com.faltenreich.release.base.date.atStartOfWeek
import com.faltenreich.release.data.model.Release
import com.faltenreich.release.data.repository.ReleaseRepository
import com.faltenreich.release.domain.release.list.ReleaseItem
import org.threeten.bp.LocalDate

class SpotlightViewModel : ViewModel() {
    private val spotlightItemsLiveData = MutableLiveData<List<SpotlightItem>>()

    private var spotlightItems: List<SpotlightItem>?
        get() = spotlightItemsLiveData.value
        set(value) = spotlightItemsLiveData.postValue(value)

    fun observeData(owner: LifecycleOwner, onObserve: (List<SpotlightItem>) -> Unit) {
        spotlightItemsLiveData.observe(owner, Observer(onObserve))

        val today = LocalDate.now()
        val (startOfWeek, endOfWeek) = today.atStartOfWeek to today.atEndOfWeek
        // Plus one for the promo
        ReleaseRepository.getBetween(startOfWeek, endOfWeek, PAGE_SIZE + 1) { weekly ->
            val lastMonth = today.minusMonths(1)
            val endOfLastWeek = startOfWeek.minusDays(1)
            ReleaseRepository.getBetween(lastMonth, endOfLastWeek, PAGE_SIZE) { recent ->
                ReleaseRepository.getSubscriptions(LocalDate.now(), PAGE_SIZE) { subscriptions ->
                    setData(weekly, recent, subscriptions)
                }
            }
        }
    }

    private fun setData(weekly: List<Release>, recent: List<Release>, subscriptions: List<Release>) {
        val items = mutableListOf<SpotlightItem>()

        addPromo(weekly, items)
        addSubscriptions(subscriptions, items)
        addWeekly(weekly, items)
        addRecent(recent, items)

        spotlightItems = items.toList()
    }

    private fun addPromo(from: List<Release>, to: MutableList<SpotlightItem>) {
        from.firstOrNull()?.let { release -> to.add(SpotlightPromoItem(release)) }
    }

    private fun addSubscriptions(from: List<Release>, to: MutableList<SpotlightItem>) {
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
        from.drop(1).takeIf(List<*>::isNotEmpty)?.let { releases ->
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
        from.takeIf(List<*>::isNotEmpty)?.let { releases ->
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
        private const val PAGE_SIZE = 5
    }
}
