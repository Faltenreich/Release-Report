package com.faltenreich.releaseradar.ui.view

import android.widget.ImageView
import com.bumptech.glide.Glide

// TODO: Placeholder and scale
fun ImageView.setImageAsync(url: String) = Glide.with(this).load(url).into(this)