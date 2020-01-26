package com.faltenreich.release.domain.media.image

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.faltenreich.release.framework.scrollgalleryview.GlideImageLoader
import com.veinhorn.scrollgalleryview.MediaInfo
import com.veinhorn.scrollgalleryview.builder.GallerySettings

class ImageGalleryViewModel : ViewModel() {

    private val mediaLiveData = MutableLiveData<List<MediaInfo>?>()

    var media: List<MediaInfo>?
        get() = mediaLiveData.value
        set(value) = mediaLiveData.postValue(value)

    fun observeMedia(imageUrls: Array<String>, owner: LifecycleOwner, onObserve: (List<MediaInfo>?) -> Unit) {
        mediaLiveData.observe(owner, Observer(onObserve))
        media = imageUrls.map { imageUrl -> MediaInfo.mediaLoader(GlideImageLoader(imageUrl)) }
    }

    fun createGallerySettings(fragmentManager: FragmentManager): GallerySettings {
        return GallerySettings
            .from(fragmentManager)
            .thumbnailSize(THUMBNAIL_SIZE)
            .enableZoom(true)
            .build()
    }

    companion object {
        private const val THUMBNAIL_SIZE = 400
    }
}