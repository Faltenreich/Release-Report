package com.faltenreich.releaseradar.data.viewmodel

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.faltenreich.releaseradar.ui.view.TintAction

class MainViewModel : ViewModel() {
    private val tintLiveData = MutableLiveData<TintAction>()

    var tint: TintAction?
        get() = tintLiveData.value
        set(value) = tintLiveData.postValue(value)

    fun observeTint(owner: LifecycleOwner, onObserve: (TintAction) -> Unit) {
        tintLiveData.observe(owner, Observer(onObserve))
    }
}