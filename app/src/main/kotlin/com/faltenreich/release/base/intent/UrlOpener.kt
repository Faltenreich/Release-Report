package com.faltenreich.release.base.intent

import android.content.Context
import android.content.Intent
import android.net.Uri

interface UrlOpener {

    fun openUrl(context: Context, url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        if (intent.resolveActivity(context.packageManager) != null) {
            context.startActivity(intent)
        }
    }
}