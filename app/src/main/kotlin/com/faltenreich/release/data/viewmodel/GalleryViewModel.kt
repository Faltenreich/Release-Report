package com.faltenreich.release.data.viewmodel

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.faltenreich.release.R
import com.faltenreich.release.data.model.Genre
import com.faltenreich.release.data.model.Media
import com.faltenreich.release.data.model.Platform
import com.faltenreich.release.data.model.Release
import com.faltenreich.release.data.repository.MediaRepository
import com.faltenreich.release.data.repository.ReleaseRepository
import com.faltenreich.release.data.repository.RepositoryFactory

class GalleryViewModel : ViewModel() {
    private val releaseRepository = RepositoryFactory.repository<ReleaseRepository>()
    private val mediaRepository = RepositoryFactory.repository<MediaRepository>()

    private val releaseLiveData = MutableLiveData<Release>()
    private val mediaLiveData = MutableLiveData<List<Media>>()

    var release: Release?
        get() = releaseLiveData.value
        set(value) = releaseLiveData.postValue(value)

    var media: List<Media>?
        get() = mediaLiveData.value
        set(value) = mediaLiveData.postValue(value)

    fun observeRelease(id: String, owner: LifecycleOwner, onObserve: (Release?) -> Unit) {
        releaseLiveData.observe(owner, Observer(onObserve))
        releaseRepository.getById(id) { release -> this.release = release }
    }

    fun observeMedia(release: Release, owner: LifecycleOwner, onObserve: (List<Media>?) -> Unit) {
        mediaLiveData.observe(owner, Observer(onObserve))
        release.media?.let { ids ->
            mediaRepository.getByIds(ids) { media -> this.media = media }
        }
    }
}