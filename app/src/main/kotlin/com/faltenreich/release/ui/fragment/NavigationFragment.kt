package com.faltenreich.release.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.core.view.children
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.faltenreich.release.R
import kotlinx.android.synthetic.main.fragment_navigation.*

class NavigationFragment : BaseBottomSheetDialogFragment(R.layout.fragment_navigation) {
    private val previousDestinationId: Int? by lazy { arguments?.let { bundle -> NavigationFragmentArgs.fromBundle(bundle).previousDestinationId } }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Workaround: Selection does not work, since checked destination is NavigationFragment
        navigationView.setupWithNavController(findNavController())
        selectCurrentDestination()
    }

    // Workaround: Preselect current destination manually, since selection does not work due to checked destination being NavigationFragment
    private fun selectCurrentDestination() {
        navigationView.menu.children.forEach { menuItem -> menuItem.isChecked = previousDestinationId == menuItem.itemId }
    }

    companion object {
        const val ARGUMENT_PREVIOUS_DESTINATION_ID = "previousDestinationId"
    }
}