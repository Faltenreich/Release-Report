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
import com.faltenreich.release.data.repository.*

class ReleaseDetailViewModel : ViewModel() {
    private val releaseRepository = RepositoryFactory.repository<ReleaseRepository>()
    private val genreRepository = RepositoryFactory.repository<GenreRepository>()
    private val platformRepository = RepositoryFactory.repository<PlatformRepository>()
    private val imageRepository = RepositoryFactory.repository<MediaRepository>()

    private val releaseLiveData = MutableLiveData<Release>()
    private val genreLiveData = MutableLiveData<List<Genre>>()
    private val platformLiveData = MutableLiveData<List<Platform>>()
    private val imageLiveData = MutableLiveData<List<Media>>()

    var release: Release?
        get() = releaseLiveData.value
        set(value) = releaseLiveData.postValue(value)

    var genres: List<Genre>?
        get() = genreLiveData.value
        set(value) = genreLiveData.postValue(value)

    var platforms: List<Platform>?
        get() = platformLiveData.value
        set(value) = platformLiveData.postValue(value)

    var media: List<Media>?
        get() = imageLiveData.value
        set(value) = imageLiveData.postValue(value)

    val color: Int
        get() = release?.releaseType?.colorResId ?: R.color.colorPrimary

    val colorDark
        get() = release?.releaseType?.colorDarkResId ?: R.color.colorPrimaryDark

    fun observeRelease(id: String, owner: LifecycleOwner, onObserve: (Release?) -> Unit) {
        releaseLiveData.observe(owner, Observer(onObserve))
        releaseRepository.getById(id) { release = it }
    }

    fun observeGenres(release: Release, owner: LifecycleOwner, onObserve: (List<Genre>?) -> Unit) {
        genreLiveData.observe(owner, Observer(onObserve))
        release.genres?.takeIf(List<String>::isNotEmpty)?.let { ids ->
            genreRepository.getByIds(ids) { genres -> this.genres = genres }
        }
    }

    fun observePlatforms(release: Release, owner: LifecycleOwner, onObserve: (List<Platform>?) -> Unit) {
        platformLiveData.observe(owner, Observer(onObserve))
        release.platforms?.takeIf(List<String>::isNotEmpty)?.let { ids ->
            platformRepository.getByIds(ids) { platforms -> this.platforms = platforms }
        }
    }

    fun observeMedia(release: Release, owner: LifecycleOwner, onObserve: (List<Media>?) -> Unit) {
        imageLiveData.observe(owner, Observer(onObserve))
        release.media?.takeIf(List<String>::isNotEmpty)?.let { ids ->
            imageRepository.getByIds(ids) { images -> this.media = images }
        }
    }
}