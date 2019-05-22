package com.faltenreich.releaseradar.data.viewmodel

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.faltenreich.releaseradar.R
import com.faltenreich.releaseradar.data.model.Genre
import com.faltenreich.releaseradar.data.model.Image
import com.faltenreich.releaseradar.data.model.Platform
import com.faltenreich.releaseradar.data.model.Release
import com.faltenreich.releaseradar.data.repository.RepositoryFactory

class ReleaseDetailViewModel : ViewModel() {
    private val releaseRepository = RepositoryFactory.repositoryForReleases()
    private val genreRepository = RepositoryFactory.repositoryForGenres()
    private val platformRepository = RepositoryFactory.repositoryForPlatforms()
    private val imageRepository = RepositoryFactory.repositoryForImages()

    private val releaseLiveData = MutableLiveData<Release>()
    private val genreLiveData = MutableLiveData<List<Genre>>()
    private val platformLiveData = MutableLiveData<List<Platform>>()
    private val imageLiveData = MutableLiveData<List<Image>>()

    var release: Release?
        get() = releaseLiveData.value
        set(value) = releaseLiveData.postValue(value)

    var genres: List<Genre>?
        get() = genreLiveData.value
        set(value) = genreLiveData.postValue(value)

    var platforms: List<Platform>?
        get() = platformLiveData.value
        set(value) = platformLiveData.postValue(value)

    var images: List<Image>?
        get() = imageLiveData.value
        set(value) = imageLiveData.postValue(value)

    val color: Int
        get() = release?.mediaType?.colorResId ?: R.color.colorPrimary

    val colorDark
        get() = release?.mediaType?.colorDarkResId ?: R.color.colorPrimaryDark

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

    fun observeImages(release: Release, owner: LifecycleOwner, onObserve: (List<Image>?) -> Unit) {
        imageLiveData.observe(owner, Observer(onObserve))
        release.images?.takeIf(List<String>::isNotEmpty)?.let { ids ->
            imageRepository.getByIds(ids) { images -> this.images = images }
        }
    }
}