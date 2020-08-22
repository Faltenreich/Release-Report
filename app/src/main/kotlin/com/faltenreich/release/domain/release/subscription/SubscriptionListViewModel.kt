package com.faltenreich.release.domain.release.subscription

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.faltenreich.release.data.repository.ReleaseRepository
import com.faltenreich.release.domain.release.list.ReleaseItem
import com.faltenreich.release.domain.release.list.ReleaseProvider
import com.faltenreich.release.framework.android.architecture.LiveDataFix
import kotlinx.coroutines.launch

class SubscriptionListViewModel : ViewModel() {

    private var releasesLiveData = LiveDataFix<List<ReleaseProvider>?>()
    var releases: List<ReleaseProvider>?
        get() = releasesLiveData.value
        set(value) = releasesLiveData.postValue(value)

    fun observeReleases(owner: LifecycleOwner, onObserve: (List<ReleaseProvider>?) -> Unit) {
        releasesLiveData.observe(owner, Observer(onObserve))
        viewModelScope.launch {
            releases = ReleaseRepository.getSubscriptions().mapNotNull { release ->
                release.releaseDate?.let { date -> ReleaseItem(release, date) }
            }
        }
    }
}
