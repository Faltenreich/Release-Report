package com.faltenreich.releaseradar.ui.fragment

import android.os.Bundle
import android.view.*
import androidx.annotation.LayoutRes
import androidx.annotation.MenuRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.faltenreich.releaseradar.data.provider.ViewModelCreator
import com.faltenreich.releaseradar.ui.react.ToolbarDelegate
import kotlin.reflect.KClass

abstract class BaseFragment(
    @LayoutRes private val layoutResId: Int,
    @MenuRes private val menuResId: Int? = null,
    @StringRes private val titleResId: Int? = null,
    @StringRes private val subtitleResId: Int? = null
) : Fragment(), ViewModelCreator {

    var title: String? = null
        set(value) {
            field = value
            activity?.title = value
        }

    var subtitle: String? = null

    protected val toolbarDelegate: ToolbarDelegate?
        get() = activity as? ToolbarDelegate

    override fun <T : ViewModel> createViewModel(clazz: KClass<T>): T = ViewModelProviders.of(this).get(clazz.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(menuResId != null)
        titleResId?.let { titleResId -> title = getString(titleResId) }
        subtitleResId?.let { titleResId -> subtitle = getString(titleResId) }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View = inflater.inflate(layoutResId, container, false)

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        menuResId?.let { menuResId -> inflater?.inflate(menuResId, menu) }
    }

    protected fun finish() {
        view?.findNavController()?.navigateUp()
    }
}