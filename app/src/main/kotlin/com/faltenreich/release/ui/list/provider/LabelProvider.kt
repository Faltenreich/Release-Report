package com.faltenreich.release.ui.list.provider

import android.content.Context

interface LabelProvider {
    fun print(context: Context): String?
}