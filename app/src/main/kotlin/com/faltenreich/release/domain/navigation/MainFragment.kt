package com.faltenreich.release.domain.navigation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.faltenreich.release.R
import com.faltenreich.release.framework.android.animation.ColorAnimation
import com.faltenreich.release.framework.android.animation.ViewBackgroundColorProvider
import com.faltenreich.release.framework.android.fragment.BaseFragment
import com.faltenreich.release.framework.android.view.fab.foregroundTint
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : BaseFragment(R.layout.fragment_main) {

    private val viewModel by activityViewModels<MainViewModel>()

    private val navigationHostFragment: NavHostFragment
        get() = childFragmentManager.findFragmentById(R.id.mainNavigationHost) as NavHostFragment

    private val navigationController: NavController
        get() = navigationHostFragment.navController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLayout()
        observeData()
    }

    private fun initLayout() {
        appCompatActivity?.setSupportActionBar(bottomAppBar)
        bottomAppBar.setNavigationOnClickListener { openNavigation() }
        navigationController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.gallery) {
                bottomAppBar.performHide()
            } else {
                bottomAppBar.performShow()
            }
        }
        fab.hide()
        fab.setOnClickListener { viewModel.fabConfig?.onClick?.invoke() }
    }

    private fun observeData() {
        viewModel.observeFabConfig(this, ::setFabConfig)
    }

    private fun setFabConfig(fabConfig: FabConfig?) {
        if (fabConfig != null) {
            if (fab.isOrWillBeHidden) {
                fab.show()
            }
            fab.setImageDrawable(fabConfig.icon)
            fab.foregroundTint = fabConfig.foregroundColor
            ColorAnimation(ViewBackgroundColorProvider(fab), fabConfig.backgroundColor).start()
        } else {
            if (fab.isOrWillBeShown) {
                fab.hide()
            }
        }
    }

    private fun openNavigation() {
        val arguments = navigationController.currentDestination?.id?.let { id ->
            Bundle().apply { putInt(NavigationFragment.ARGUMENT_PREVIOUS_DESTINATION_ID, id) }
        }
        navigationController.navigate(R.id.navigation, arguments)
    }
}