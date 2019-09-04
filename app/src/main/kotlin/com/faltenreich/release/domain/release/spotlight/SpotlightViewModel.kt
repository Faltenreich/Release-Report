package com.faltenreich.release.domain.release.spotlight

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.faltenreich.release.R
import com.faltenreich.release.base.date.calendarWeek
import com.faltenreich.release.base.primitive.isTrue
import com.faltenreich.release.data.model.Release
import com.faltenreich.release.data.repository.ReleaseRepository
import com.faltenreich.release.domain.preference.FavoriteManager
import com.faltenreich.release.domain.release.list.ReleaseItem
import org.threeten.bp.DayOfWeek
import org.threeten.bp.LocalDate

class SpotlightViewModel : ViewModel() {
    private val spotlightItemsLiveData = MutableLiveData<List<SpotlightItem>>()
    private val today by lazy { LocalDate.now() }

    var spotlightItems: List<SpotlightItem>?
        get() = spotlightItemsLiveData.value
        set(value) = spotlightItemsLiveData.postValue(value)

    fun observeData(owner: LifecycleOwner, onObserve: (List<SpotlightItem>) -> Unit) {
        spotlightItemsLiveData.observe(owner, Observer(onObserve))

        val startAt = today.minusMonths(1)
        val endAt = today.with(DayOfWeek.SUNDAY)

        ReleaseRepository.getBetween(startAt, endAt, PAGE_SIZE) { releases -> setData(releases) }
    }

    private fun setData(data: List<Release>) {
        val items = mutableListOf<SpotlightItem>()
        val (weekly, recent) = data.partition { release -> release.releaseDate?.calendarWeek == today.calendarWeek }

        weekly.firstOrNull()?.let { release -> items.add(SpotlightPromoItem(release)) }

        FavoriteManager.getFavorites().filter { release ->
            release.releaseDate?.calendarWeek?.let { calendarWeek ->
                calendarWeek >= today.calendarWeek
            }.isTrue
        }.sortedBy(Release::releaseDate).take(5).takeIf(List<Any>::isNotEmpty)?.let { releases ->
            items.add(
                SpotlightReleaseItem(
                    R.string.for_you,
                    releases.mapNotNull { release ->
                        release.releaseDate?.let { date -> ReleaseItem(release, date) }
                    },
                    totalReleaseCount = null
                )
            )
        }

        weekly.drop(1).take(5).takeIf(List<Any>::isNotEmpty)?.let { releases ->
            items.add(
                SpotlightReleaseItem(
                    R.string.this_week,
                    releases.mapNotNull { release ->
                        release.releaseDate?.let { date -> ReleaseItem(release, date) }
                    },
                    totalReleaseCount = weekly.size
                )
            )
        }

        recent.take(5).takeIf(List<Any>::isNotEmpty)?.let { releases ->
            items.add(
                SpotlightReleaseItem(
                    R.string.recently,
                    releases.mapNotNull { release ->
                        release.releaseDate?.let { date -> ReleaseItem(release, date) }
                    },
                    totalReleaseCount = null
                )
            )
        }

        spotlightItems = items.toList()
    }

    companion object {
        private const val PAGE_SIZE = 25
    }
}
