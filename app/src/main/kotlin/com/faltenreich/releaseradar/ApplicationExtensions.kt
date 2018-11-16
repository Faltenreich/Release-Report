package com.faltenreich.releaseradar

import android.content.res.ColorStateList
import android.widget.ImageView
import com.bumptech.glide.Glide

// TODO: Placeholder and scale
fun ImageView.setImageAsync(url: String) = Glide.with(this).load(url).into(this)

var ImageView.tint: Int
    get() = TODO()
    set(value) { imageTintList = ColorStateList.valueOf(value) }