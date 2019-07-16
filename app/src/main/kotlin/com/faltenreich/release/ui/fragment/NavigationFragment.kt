package com.faltenreich.release.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.faltenreich.release.R
import kotlinx.android.synthetic.main.fragment_navigation.*

class NavigationFragment : BaseBottomSheetDialogFragment(R.layout.fragment_navigation) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigationView.setupWithNavController(findNavController())
    }
}