package com.faltenreich.release.framework.android.context

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Point
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
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

fun Context.showToast(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_LONG).show()
}

fun Context.showToast(textRes: Int) {
    showToast(getString(textRes))
}

val Context.screenSize: Point
    get() = Point().also { point ->
        val windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        windowManager.defaultDisplay.getSize(point)
    }


@ColorInt
fun Context.getColorFromAttribute(@AttrRes attrRes: Int): Int {
    val typedValue = TypedValue()
    val theme = theme
    val wasResolved = theme.resolveAttribute(attrRes, typedValue, true)
    return if (wasResolved)
        if (typedValue.resourceId == 0) typedValue.data
        else ContextCompat.getColor(this, typedValue.resourceId)
    else -1
}