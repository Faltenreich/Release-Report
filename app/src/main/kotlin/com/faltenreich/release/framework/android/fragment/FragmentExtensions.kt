package com.faltenreich.release.framework.android.fragment

import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.faltenreich.release.framework.android.context.hideKeyboard

fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}

fun DialogFragment.showSafely(manager: FragmentManager?, tag: String? = null) {
    manager?.let { show(it, tag) }
}