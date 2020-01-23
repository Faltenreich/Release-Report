package com.faltenreich.release.domain.release.detail

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.faltenreich.release.R
import com.faltenreich.release.data.model.Release
import com.faltenreich.release.data.repository.ReleaseRepository

class ReleaseDetailViewModel : ViewModel() {

    private val releaseLiveData = MutableLiveData<Release>()

    var release: Release?
        get() = releaseLiveData.value
        set(value) = releaseLiveData.postValue(value)

    val color: Int
        get() = release?.releaseType?.colorResId ?: R.color.colorPrimary

    val colorDark
        get() = release?.releaseType?.colorDarkResId ?: R.color.colorPrimaryDark

    fun observeRelease(id: String, owner: LifecycleOwner, onObserve: (Release?) -> Unit) {
        releaseLiveData.observe(owner, Observer(onObserve))
        ReleaseRepository.getById(id) { release -> this.release = release }
    }
}