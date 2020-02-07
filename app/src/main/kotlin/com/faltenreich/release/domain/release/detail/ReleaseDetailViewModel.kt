package com.faltenreich.release.domain.release.detail

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.faltenreich.release.data.model.Release
import com.faltenreich.release.framework.androidx.LiveDataFix
import com.faltenreich.release.framework.kotlinx.JsonParser

class ReleaseDetailViewModel : ViewModel() {

    private val releaseLiveData = LiveDataFix<Release?>()
    var release: Release?
        get() = releaseLiveData.value
        set(value) = releaseLiveData.postValue(value)

    fun observeRelease(releaseAsJson: String, owner: LifecycleOwner, onObserve: (Release?) -> Unit) {
        releaseLiveData.observe(owner, Observer(onObserve))
        release = JsonParser.parseFromJson(Release.serializer(), releaseAsJson)
    }
}