package com.faltenreich.releaseradar.data.viewmodel

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.faltenreich.releaseradar.data.dao.Query
import com.faltenreich.releaseradar.data.enum.MediaType
import com.faltenreich.releaseradar.data.model.Release
import com.faltenreich.releaseradar.data.repository.ReleaseRepository
import com.faltenreich.releaseradar.extension.calendarWeek
import org.threeten.bp.LocalDate

class SpotlightViewModel : ViewModel() {

    private val releasesOfWeekLiveData = MutableLiveData<List<Release>>()

    var releasesOfWeek: List<Release>?
        get() = releasesOfWeekLiveData.value
        set(value) = releasesOfWeekLiveData.postValue(value)

    fun observeReleasesOfWeek(type: MediaType, owner: LifecycleOwner, onObserve: (List<Release>) -> Unit) {
        val today = LocalDate.now()
        val filter = "${type.key}-${today.year}-${today.calendarWeek}-"
        releasesOfWeekLiveData.observe(owner, Observer { releases -> onObserve(releases) })
        ReleaseRepository.getAll(
            Query(
                orderBy = "indexForSpotlight",
                limitToFirst = 10,
                startAt = filter to null,
                endAt = "$filter\uf8ff" to null
            ), onSuccess = { releases ->
                releasesOfWeek = releases
            }, onError = {
                releasesOfWeek = null
            })
    }
}
