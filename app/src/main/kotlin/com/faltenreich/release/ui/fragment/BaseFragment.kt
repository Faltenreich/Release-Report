package com.faltenreich.release.ui.fragment

import android.os.Bundle
import android.view.*
import androidx.annotation.LayoutRes
import androidx.annotation.MenuRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.faltenreich.release.data.provider.ViewModelCreator
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

    val appCompatActivity: AppCompatActivity?
        get() = activity as? AppCompatActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(layoutResId, container, false)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menuResId?.let { menuResId -> inflater.inflate(menuResId, menu) }
    }

    override fun <T : ViewModel> createViewModel(clazz: KClass<T>): T {
        return ViewModelProviders.of(this).get(clazz.java)
    }

    private fun init() {
        setHasOptionsMenu(menuResId != null)
        titleResId?.let { titleResId -> title = getString(titleResId) }
        subtitleResId?.let { titleResId -> subtitle = getString(titleResId) }
    }

    protected fun finish() {
        findNavController().navigateUp()
    }
}