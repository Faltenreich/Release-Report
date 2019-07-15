package com.faltenreich.release.ui.activity

import androidx.navigation.findNavController
import com.faltenreich.release.R

class MainActivity : BaseActivity(R.layout.activity_main) {
    override fun onBackPressed() {
        if (!findNavController(R.id.mainNavigationHost).navigateUp()) {
            super.onBackPressed()
        }
    }
}