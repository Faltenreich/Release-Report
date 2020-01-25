package com.faltenreich.release.domain.release.detail

import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.faltenreich.release.R
import com.faltenreich.release.domain.media.image.ImageListFragment
import com.faltenreich.release.domain.media.video.VideoListFragment
import com.faltenreich.release.framework.android.viewpager.ViewPager2FragmentAdapter

class ReleaseDetailFragmentAdapter(host: Fragment) : ViewPager2FragmentAdapter(host) {

    private enum class Pages(@StringRes val titleRes: Int) {
        INFO(R.string.info),
        IMAGES(R.string.images),
        VIDEOS(R.string.videos)
    }

    override fun getItemCount(): Int {
        return Pages.values().size
    }

    override fun createFragment(position: Int): Fragment {
        return when (Pages.values()[position]) {
            Pages.INFO -> ReleaseInfoFragment()
            Pages.IMAGES -> ImageListFragment()
            Pages.VIDEOS -> VideoListFragment()
        }
    }

    override fun getPageTitle(position: Int): String? {
        return context?.getString(Pages.values()[position].titleRes)
    }
}
