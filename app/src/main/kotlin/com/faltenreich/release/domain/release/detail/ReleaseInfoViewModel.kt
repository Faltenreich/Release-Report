package com.faltenreich.release.domain.release.detail

import androidx.annotation.ColorRes
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.faltenreich.release.R
import com.faltenreich.release.data.model.Genre
import com.faltenreich.release.data.model.Platform
import com.faltenreich.release.data.model.Release
import com.faltenreich.release.data.repository.GenreRepository
import com.faltenreich.release.data.repository.PlatformRepository
import com.faltenreich.release.framework.androidx.LiveDataFix
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class ReleaseInfoViewModel : ViewModel() {

    private val releaseLiveData = LiveDataFix<Release?>()
    var release: Release?
        get() = releaseLiveData.value
        set(value) = releaseLiveData.postValue(value)

    private val genreLiveData = LiveDataFix<List<Genre>?>()
    var genres: List<Genre>?
        get() = genreLiveData.value
        set(value) = genreLiveData.postValue(value)

    private val platformLiveData = LiveDataFix<List<Platform>?>()
    var platforms: List<Platform>?
        get() = platformLiveData.value
        set(value) = platformLiveData.postValue(value)

    @get:ColorRes
    val color: Int
        get() = release?.releaseType?.colorResId ?: R.color.colorPrimary

    @get:ColorRes
    val colorDark: Int
        get() = release?.releaseType?.colorDarkResId ?: R.color.colorPrimaryDark

    fun observeRelease(owner: LifecycleOwner, onObserve: (Release?) -> Unit) {
        releaseLiveData.observe(owner, Observer { release ->
            onObserve(release)
            release?.genres?.let { ids -> fetchGenres(ids) }
            release?.platforms?.let { ids -> fetchPlatforms(ids) }
        })
    }

    fun observeGenres(owner: LifecycleOwner, onObserve: (List<Genre>?) -> Unit) {
        genreLiveData.observe(owner, Observer(onObserve))
    }

    fun observePlatforms(owner: LifecycleOwner, onObserve: (List<Platform>?) -> Unit) {
        platformLiveData.observe(owner, Observer(onObserve))
    }

    private fun fetchPlatforms(ids: List<String>) {
        viewModelScope.launch(Dispatchers.IO) {
            platforms = PlatformRepository.getByIds(ids).sortedBy { platform ->
                platform.title?.toLowerCase(Locale.getDefault())
            }
        }
    }

    private fun fetchGenres(ids: List<String>) {
        viewModelScope.launch(Dispatchers.IO) {
            genres = GenreRepository.getByIds(ids).sortedBy { genre ->
                genre.title?.toLowerCase(Locale.getDefault())
            }
        }
    }
}