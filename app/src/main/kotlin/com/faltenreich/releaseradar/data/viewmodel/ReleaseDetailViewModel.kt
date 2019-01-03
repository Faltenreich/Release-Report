package com.faltenreich.releaseradar.data.viewmodel

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.faltenreich.releaseradar.data.model.Genre
import com.faltenreich.releaseradar.data.model.Release
import com.faltenreich.releaseradar.data.repository.ReleaseRepository

class ReleaseDetailViewModel : ViewModel() {

    private val releaseLiveData = MutableLiveData<Release>()
    private val genreLiveData = MutableLiveData<List<Genre>>()

    var release: Release?
        get() = releaseLiveData.value
        set(value) = releaseLiveData.postValue(value)

    var genres: List<Genre>?
        get() = genreLiveData.value
        set(value) = genreLiveData.postValue(value)

    fun observeRelease(id: String, owner: LifecycleOwner, onObserve: (Release?) -> Unit) {
        releaseLiveData.observe(owner, Observer { release -> onObserve(release) })
        ReleaseRepository.getById(id, onSuccess = { release = it }, onError = { release = null })
    }

    fun observeGenres(release: Release, owner: LifecycleOwner, onObserve: (List<Genre>?) -> Unit) {
        genreLiveData.observe(owner, Observer { genres -> onObserve(genres) })
        // TODO: Replace when functional
        val genreAction = Genre().apply { id = "0"; name = "Action" }
        val genreComedy = Genre().apply { id = "1"; name = "Comedy" }
        genres = listOf(genreAction, genreComedy)
    }
}