package com.faltenreich.release.base.intent

import android.app.SearchManager
import android.content.Context
import android.content.Intent


interface WebSearchOpener {

    fun searchInWeb(context: Context?, query: String?) {
        val intent = Intent(Intent.ACTION_WEB_SEARCH)
        intent.putExtra(SearchManager.QUERY, query)
        context?.startActivity(intent)
    }
}