package com.faltenreich.release.domain.release.detail

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.faltenreich.release.data.model.Genre
import com.faltenreich.release.data.model.Platform
import com.faltenreich.release.data.model.Release
import com.faltenreich.release.data.repository.GenreRepository
import com.faltenreich.release.data.repository.PlatformRepository
import com.faltenreich.release.framework.androidx.LiveDataFix
import java.util.*

class ReleaseInfoViewModel : ViewModel() {

    private val releaseLiveData = LiveDataFix<Release?>()
    var release: Release?
        get() = releaseLiveData.value
        set(value) = releaseLiveData.postValue(value)

    private val genreLiveData = LiveDataFix<List<Genre>?>()
    private var genres: List<Genre>?
        get() = genreLiveData.value
        set(value) = genreLiveData.postValue(value)

    private val platformLiveData = LiveDataFix<List<Platform>?>()
    private var platforms: List<Platform>?
        get() = platformLiveData.value
        set(value) = platformLiveData.postValue(value)

    fun observeRelease(owner: LifecycleOwner, onObserve: (Release?) -> Unit) {
        releaseLiveData.observe(owner, Observer { release ->
            onObserve(release)
            release?.genres?.let { ids -> fetchGenres(ids) }
            release?.platforms?.let { ids -> fetchPlatforms(ids) }
        })
    }

    private fun fetchPlatforms(ids: List<String>) {
        PlatformRepository.getByIds(ids) { platforms ->
            this.platforms = platforms.sortedBy { platform -> platform.title?.toLowerCase(Locale.getDefault()) }
        }
    }

    private fun fetchGenres(ids: List<String>) {
        GenreRepository.getByIds(ids) { genres ->
            this.genres = genres.sortedBy { genre -> genre.title?.toLowerCase(Locale.getDefault()) }
        }
    }

    fun observeGenres(owner: LifecycleOwner, onObserve: (List<Genre>?) -> Unit) {
        genreLiveData.observe(owner, Observer(onObserve))
    }

    fun observePlatforms(owner: LifecycleOwner, onObserve: (List<Platform>?) -> Unit) {
        platformLiveData.observe(owner, Observer(onObserve))
    }
}