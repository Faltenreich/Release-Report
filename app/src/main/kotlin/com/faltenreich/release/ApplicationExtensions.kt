package com.faltenreich.release

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

val Any.tag: String
    get() = this::class.java.simpleName

fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}

fun Activity.hideKeyboard() {
    hideKeyboard(if (currentFocus == null) View(this) else currentFocus)
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun DialogFragment.showSafely(manager: FragmentManager?, tag: String? = null) {
    manager?.let { show(it, tag) }
}

val Context.versionName: String?
    get() = try {
        packageManager.getPackageInfo(packageName, 0).versionName
    } catch (exception: PackageManager.NameNotFoundException) {
        Log.e(tag, exception.message)
        null
    }

fun Context.showToast(textRes: Int) {
    Toast.makeText(this, textRes, Toast.LENGTH_LONG).show()
}