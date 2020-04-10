package com.faltenreich.release.domain.navigation

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.faltenreich.release.R
import com.faltenreich.release.framework.android.fragment.BaseFragment
import com.faltenreich.release.framework.android.view.fab.backgroundTint
import com.faltenreich.release.framework.android.view.fab.foregroundTintResource
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
        val context = context ?: return
        val shouldAnimate = fab.isVisible
        if (fabConfig != null) {
            if (fab.isOrWillBeHidden) {
                fab.show()
            }
            fab.setImageResource(fabConfig.iconRes)
            fab.foregroundTintResource = fabConfig.foregroundColorRes

            val backgroundColorFrom = fab.backgroundTint
            val backgroundColorTo = ContextCompat.getColor(context, fabConfig.backgroundColorRes)
            if (backgroundColorFrom != backgroundColorTo) {
                if (shouldAnimate) {
                    ValueAnimator.ofObject(ArgbEvaluator(), backgroundColorFrom, backgroundColorTo).apply {
                        duration = resources.getInteger(android.R.integer.config_mediumAnimTime).toLong()
                        addUpdateListener { animator ->
                            fab.backgroundTint = animator.animatedValue as Int
                        }

                    }.start()
                } else {
                    fab.backgroundTint = backgroundColorTo
                }

            }
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