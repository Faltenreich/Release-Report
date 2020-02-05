package com.faltenreich.release.domain.release.detail

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.faltenreich.release.data.model.Release
import com.faltenreich.release.data.repository.ReleaseRepository
import com.faltenreich.release.framework.androidx.LiveDataFix
import kotlinx.coroutines.launch

class ReleaseDetailViewModel : ViewModel() {

    private val releaseLiveData = LiveDataFix<Release?>()
    var release: Release?
        get() = releaseLiveData.value
        set(value) = releaseLiveData.postValue(value)

    fun observeRelease(id: String, owner: LifecycleOwner, onObserve: (Release?) -> Unit) {
        releaseLiveData.observe(owner, Observer(onObserve))
        viewModelScope.launch { release = ReleaseRepository.getById(id) }
    }
}