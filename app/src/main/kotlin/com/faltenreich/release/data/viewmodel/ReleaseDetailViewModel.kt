package com.faltenreich.release.data.viewmodel

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.faltenreich.release.R
import com.faltenreich.release.data.model.Genre
import com.faltenreich.release.data.model.Platform
import com.faltenreich.release.data.model.Release
import com.faltenreich.release.data.repository.GenreRepository
import com.faltenreich.release.data.repository.PlatformRepository
import com.faltenreich.release.data.repository.ReleaseRepository
import com.faltenreich.release.data.repository.RepositoryFactory

class ReleaseDetailViewModel : ViewModel() {
    private val releaseRepository = RepositoryFactory.repository<ReleaseRepository>()
    private val genreRepository = RepositoryFactory.repository<GenreRepository>()
    private val platformRepository = RepositoryFactory.repository<PlatformRepository>()

    private val releaseLiveData = MutableLiveData<Release>()
    private val genreLiveData = MutableLiveData<List<Genre>>()
    private val platformLiveData = MutableLiveData<List<Platform>>()

    var release: Release?
        get() = releaseLiveData.value
        set(value) = releaseLiveData.postValue(value)

    var genres: List<Genre>?
        get() = genreLiveData.value
        set(value) = genreLiveData.postValue(value)

    var platforms: List<Platform>?
        get() = platformLiveData.value
        set(value) = platformLiveData.postValue(value)

    val color: Int
        get() = release?.releaseType?.colorResId ?: R.color.colorPrimary

    val colorDark
        get() = release?.releaseType?.colorDarkResId ?: R.color.colorPrimaryDark

    fun observeRelease(id: String, owner: LifecycleOwner, onObserve: (Release?) -> Unit) {
        releaseLiveData.observe(owner, Observer { release ->
            onObserve(release)
            release.genres?.let { ids -> genreRepository.getByIds(ids) { genres -> this.genres = genres } }
            release.platforms?.let { ids -> platformRepository.getByIds(ids) { platforms -> this.platforms = platforms } }
        })
        releaseRepository.getById(id) { release -> this.release = release }
    }

    fun observeGenres(owner: LifecycleOwner, onObserve: (List<Genre>?) -> Unit) {
        genreLiveData.observe(owner, Observer(onObserve))
    }

    fun observePlatforms(owner: LifecycleOwner, onObserve: (List<Platform>?) -> Unit) {
        platformLiveData.observe(owner, Observer(onObserve))
    }
}