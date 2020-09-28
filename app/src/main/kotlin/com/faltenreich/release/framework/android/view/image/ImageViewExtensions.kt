package com.faltenreich.release.framework.android.view.image

import android.content.res.ColorStateList
import android.widget.ImageView

var ImageView.tint: Int?
    get() = imageTintList?.defaultColor
    set(value) { imageTintList = value?.let { ColorStateList.valueOf(value) } }