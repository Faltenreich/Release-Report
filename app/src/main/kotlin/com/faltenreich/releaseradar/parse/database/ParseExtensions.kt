package com.faltenreich.releaseradar.parse.database

import com.parse.ParseObject
import com.parse.ParseQuery

fun <T : ParseObject> ParseQuery<T>.whereContainsText(key: String, query: String, caseSensitive: Boolean = false): ParseQuery<T> {
    return if (caseSensitive) {
        whereMatches(key, query)
    } else {
        whereMatches(key, query, "i")
    }
}