package com.faltenreich.releaseradar.ui

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.faltenreich.releaseradar.R
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainTest : UseCaseTest() {

    @Test
    fun show() {
        openFragment(R.id.main)
    }
}