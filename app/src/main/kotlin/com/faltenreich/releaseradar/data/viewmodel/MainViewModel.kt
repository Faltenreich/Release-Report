package com.faltenreich.releaseradar.data.viewmodel

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    private val tintLiveData = MutableLiveData<Int>()

    var tint: Int?
        get() = tintLiveData.value
        set(value) = tintLiveData.postValue(value)

    fun observeTint(owner: LifecycleOwner, onObserve: (Int?) -> Unit) {
        tintLiveData.observe(owner, Observer(onObserve))
    }
}