package com.faltenreich.release.framework.android.context

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Point
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.faltenreich.release.base.log.tag

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

val Context.versionName: String?
    get() = try {
        packageManager.getPackageInfo(packageName, 0).versionName
    } catch (exception: PackageManager.NameNotFoundException) {
        Log.e(tag, exception.message)
        null
    }

val Context.versionCode: Int?
    get() = try {
        packageManager.getPackageInfo(packageName, 0).versionCode
    } catch (exception: PackageManager.NameNotFoundException) {
        Log.e(tag, exception.message)
        null
    }

fun Context.showToast(textRes: Int) {
    Toast.makeText(this, textRes, Toast.LENGTH_LONG).show()
}

val Context.screenSize: Point
    get() = Point().also { point ->
        val windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        windowManager.defaultDisplay.getSize(point)
    }