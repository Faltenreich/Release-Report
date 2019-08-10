package com.faltenreich.release.extension

import androidx.fragment.app.Fragment

fun Fragment.invalidateOptionsMenu() {
    activity?.invalidateOptionsMenu()
}