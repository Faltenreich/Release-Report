package com.faltenreich.releaseradar.data.viewmodel

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.faltenreich.releaseradar.data.model.Release
import com.faltenreich.releaseradar.data.repository.ReleaseRepository

class ReleaseDetailViewModel : ViewModel() {

    private val releaseLiveData = MutableLiveData<Release>()

    var release: Release?
        get() = releaseLiveData.value
        set(value) = releaseLiveData.postValue(value)

    fun observeRelease(id: String, owner: LifecycleOwner, onObserve: (Release?) -> Unit) {
        releaseLiveData.observe(owner, Observer { release -> onObserve(release) })
        ReleaseRepository.getById(id, onSuccess = { release = it }, onError = { release = null })
    }
}