package com.faltenreich.release.domain.navigation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.faltenreich.release.framework.android.architecture.LiveDataFix

class MainViewModel : ViewModel() {

    private val fabConfigLiveData =
        LiveDataFix<FabConfig?>()
    var fabConfig: FabConfig?
        get() = fabConfigLiveData.value
        set(value) = fabConfigLiveData.postValue(value)

    fun observeFabConfig(owner: LifecycleOwner, onObserve: (FabConfig?) -> Unit) {
        fabConfigLiveData.observe(owner, Observer(onObserve))
    }
}