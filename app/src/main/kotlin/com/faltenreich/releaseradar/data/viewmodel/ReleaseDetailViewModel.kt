package com.faltenreich.releaseradar.data.viewmodel

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.faltenreich.releaseradar.data.model.Genre
import com.faltenreich.releaseradar.data.model.Platform
import com.faltenreich.releaseradar.data.model.Release
import com.faltenreich.releaseradar.data.preference.UserPreferences
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

    var isFavorite: Boolean
        get() = release?.id?.let { id -> UserPreferences.favoriteReleaseIds.contains(id) } ?: false
        set(value) {
            release?.id?.let { id ->
                UserPreferences.favoriteReleaseIds = UserPreferences.favoriteReleaseIds.filter { otherId -> otherId != id }.toMutableSet().apply { if (value) add(id) }
            }
        }

    fun observeRelease(id: String, owner: LifecycleOwner, onObserve: (Release?) -> Unit) {
        releaseLiveData.observe(owner, Observer { onObserve(it) })
        ReleaseRepository.getById(id, onSuccess = { release = it }, onError = { release = null })
    }

    fun observeGenres(release: Release, owner: LifecycleOwner, onObserve: (List<Genre>?) -> Unit) {
        genreLiveData.observe(owner, Observer { onObserve(it) })
        release.genres?.takeIf(List<String>::isNotEmpty)?.let { ids ->
            val new = mutableListOf<Genre>()
            ids.forEachIndexed { index, id ->
                val onNext: (Genre?) -> Unit = { entity ->
                    entity?.let { new.add(it) }
                    if (index == ids.size - 1) {
                        genres = new
                    }
                }
                GenreRepository.getById(id, onSuccess = { onNext(it) }, onError = { onNext(null) })
            }
        }
    }

    fun observePlatforms(release: Release, owner: LifecycleOwner, onObserve: (List<Platform>?) -> Unit) {
        platformLiveData.observe(owner, Observer { onObserve(it) })
        release.platforms?.takeIf(List<String>::isNotEmpty)?.let { ids ->
            val new = mutableListOf<Platform>()
            ids.forEachIndexed { index, id ->
                val onNext: (Platform?) -> Unit = { entity ->
                    entity?.let { new.add(it) }
                    if (index == ids.size - 1) {
                        platforms = new
                    }
                }
                PlatformRepository.getById(id, onSuccess = { onNext(it) }, onError = { onNext(null) })
            }
        }
    }
}