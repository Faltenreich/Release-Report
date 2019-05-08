package com.faltenreich.releaseradar.data.viewmodel

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.faltenreich.releaseradar.R
import com.faltenreich.releaseradar.data.model.Genre
import com.faltenreich.releaseradar.data.model.Platform
import com.faltenreich.releaseradar.data.model.Release
import com.faltenreich.releaseradar.data.repository.GenreRepository
import com.faltenreich.releaseradar.data.repository.PlatformRepository
import com.faltenreich.releaseradar.data.repository.ReleaseRepository

class ReleaseDetailViewModel : ViewModel() {
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
        get() = release?.mediaType?.colorResId ?: R.color.colorPrimary

    val colorDark
        get() = release?.mediaType?.colorDarkResId ?: R.color.colorPrimaryDark

    fun observeRelease(id: String, owner: LifecycleOwner, onObserve: (Release?) -> Unit) {
        releaseLiveData.observe(owner, Observer { onObserve(it) })
        ReleaseRepository.getById(id) { release = it }
    }

    fun observeGenres(release: Release, owner: LifecycleOwner, onObserve: (List<Genre>?) -> Unit) {
        genreLiveData.observe(owner, Observer { onObserve(it) })
        release.genres?.takeIf(List<String>::isNotEmpty)?.let { ids ->
            GenreRepository.getByIds(ids) { genres -> this.genres = genres }
        }
    }

    fun observePlatforms(release: Release, owner: LifecycleOwner, onObserve: (List<Platform>?) -> Unit) {
        platformLiveData.observe(owner, Observer { onObserve(it) })
        release.platforms?.takeIf(List<String>::isNotEmpty)?.let { ids ->
            PlatformRepository.getByIds(ids) { platforms -> this.platforms = platforms }
        }
    }
}