package com.faltenreich.release.ui.activity

import androidx.navigation.findNavController
import com.faltenreich.release.R

class MainActivity : BaseActivity(R.layout.activity_main) {
    override fun onBackPressed() {
        val consumed = findNavController(R.id.mainNavigationHost).navigateUp()
        if (!consumed) {
            super.onBackPressed()
        }
    }
}