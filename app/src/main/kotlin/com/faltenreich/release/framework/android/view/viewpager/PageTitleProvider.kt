package com.faltenreich.release.framework.android.view.viewpager

interface PageTitleProvider {
    fun getPageTitle(position: Int): String?
}