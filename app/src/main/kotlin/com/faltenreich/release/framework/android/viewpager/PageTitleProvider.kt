package com.faltenreich.release.framework.android.viewpager

interface PageTitleProvider {
    fun getPageTitle(position: Int): String
}