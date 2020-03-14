package com.faltenreich.release.domain.release.calendar

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.faltenreich.release.base.pagination.PagingDataFactory
import com.faltenreich.release.data.model.CalendarEvent
import com.faltenreich.release.data.model.Release
import com.faltenreich.release.data.repository.CalendarEventRepository
import com.faltenreich.release.data.repository.ReleaseRepository
import com.faltenreich.release.framework.android.architecture.LiveDataFix
import org.threeten.bp.LocalDate
import org.threeten.bp.YearMonth

class CalendarViewModel : ViewModel() {

    private val yearMonthLiveData =
        LiveDataFix<YearMonth?>()
    var yearMonth: YearMonth?
        get() = yearMonthLiveData.value
        set(value) = yearMonthLiveData.postValue(value)

    private lateinit var releasesLiveData: LiveData<PagedList<CalendarItem>?>

    fun observeReleases(
        yearMonth: YearMonth,
        owner: LifecycleOwner,
        onObserve: (PagedList<CalendarItem>?) -> Unit
    ) {
        val dataSource = CalendarDataSource(yearMonth)
        val dataFactory = PagingDataFactory(dataSource, PAGE_SIZE_IN_MONTHS)
        releasesLiveData = LivePagedListBuilder(dataFactory, dataFactory.config).build()
        releasesLiveData.observe(owner, Observer(onObserve))
    }

    suspend fun getCalendarEvents(start: LocalDate, end: LocalDate): List<CalendarEvent> {
        val calendarItems = CalendarEventRepository.getBetween(start, end)
            .associateBy(CalendarEvent::date)
            .toMutableMap()
        val subscriptions = ReleaseRepository.getSubscriptions(start, end)
            .sortedBy(Release::popularity)
            .associateBy(Release::releaseDate)
        subscriptions.values.forEach { release ->
            calendarItems[release.releaseDate] = CalendarEvent().apply {
                date = release.releaseDate
                imageUrl = release.imageUrlForThumbnail
                isSubscribed = true
            }
        }
        return calendarItems.values.toList()
    }

    companion object {
        private const val PAGE_SIZE_IN_MONTHS = 3
    }
}