package com.faltenreich.release.domain.media.image

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel

class ImageListViewModel : ViewModel() {

    private val imageUrlsLiveData = MutableLiveData<List<String>>()

    var imageUrls: List<String>?
        get() = imageUrlsLiveData.value
        set(value) = imageUrlsLiveData.postValue(value)

    fun observeImageUrls(owner: LifecycleOwner, onObserve: (List<String>) -> Unit) {
        imageUrlsLiveData.observe(owner, Observer(onObserve))
    }
}