package com.faltenreich.releaseradar.data.viewmodel

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.faltenreich.releaseradar.data.asString
import com.faltenreich.releaseradar.data.dao.Query
import com.faltenreich.releaseradar.data.model.Release
import com.faltenreich.releaseradar.data.repository.ReleaseRepository
import org.threeten.bp.LocalDate

class CalendarViewModel : ViewModel() {

    private val dateLiveData = MutableLiveData<LocalDate>()

    var date: LocalDate?
        get() = dateLiveData.value
        set(value) = dateLiveData.postValue(value)

    private val releaseLiveData = MutableLiveData<List<Release>>()

    var releases: List<Release>
        get() = releaseLiveData.value ?: listOf()
        set(value) = releaseLiveData.postValue(value)

    fun observeReleases(owner: LifecycleOwner, onObserve: (List<Release>) -> Unit) {
        releaseLiveData.observe(owner, Observer { releases -> onObserve(releases) })
        ReleaseRepository.getAll(
            Query(
                orderBy = "releasedAt",
                startAt = LocalDate.now().asString
            ), onSuccess = { releases = it }, onError = { releases = listOf() })
    }
}