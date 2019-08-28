package com.faltenreich.release.data.provider

import android.content.Context

interface LabelProvider {
    fun print(context: Context): String?
}