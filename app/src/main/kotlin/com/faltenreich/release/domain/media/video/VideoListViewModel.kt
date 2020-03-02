package com.faltenreich.release.domain.media.video

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.faltenreich.release.framework.android.architecture.LiveDataFix

class VideoListViewModel : ViewModel() {

    private val videoUrlsLiveData =
        LiveDataFix<List<String>?>()
    var videoUrls: List<String>?
        get() = videoUrlsLiveData.value
        set(value) = videoUrlsLiveData.postValue(value)

    fun observeVideoUrls(owner: LifecycleOwner, onObserve: (List<String>?) -> Unit) {
        videoUrlsLiveData.observe(owner, Observer(onObserve))
    }
}