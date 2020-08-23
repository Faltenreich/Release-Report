package com.faltenreich.release.framework.android.fragment

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.activity.OnBackPressedCallback
import androidx.annotation.LayoutRes
import androidx.annotation.MenuRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.faltenreich.release.base.primitive.isTrue
import com.faltenreich.release.domain.navigation.MainViewModel

abstract class BaseFragment(
    @LayoutRes private val layoutResId: Int,
    @MenuRes private val menuResId: Int? = null,
    @StringRes private val titleResId: Int? = null
) : Fragment() {

    protected val mainViewModel by activityViewModels<MainViewModel>()

    protected var isViewCreated: Boolean = false

    var title: String? = null
        set(value) {
            field = value
            activity?.title = value
        }

    val appCompatActivity: AppCompatActivity?
        get() = activity as? AppCompatActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        // Delegate back presses to navigation components
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val consumed = findNavController().navigateUp().isTrue
                if (!consumed) {
                    activity?.finish()
                }
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(layoutResId, container, false)
    }

    override fun onStart() {
        super.onStart()
        isViewCreated = true
    }

    override fun onResume() {
        super.onResume()
        hideKeyboard()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menuResId?.let { menuResId -> inflater.inflate(menuResId, menu) }
    }

    private fun init() {
        setHasOptionsMenu(menuResId != null)
        titleResId?.let { titleResId -> title = getString(titleResId) }
    }

    protected fun finish() {
        findNavController().navigateUp()
    }
}