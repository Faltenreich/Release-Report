package com.faltenreich.release.ui.logic.provider

import android.content.Context

interface LabelProvider {
    fun print(context: Context): String?
}