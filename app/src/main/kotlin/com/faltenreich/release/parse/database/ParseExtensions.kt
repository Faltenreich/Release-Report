package com.faltenreich.release.parse.database

import com.parse.ParseObject
import com.parse.ParseQuery

fun <T : ParseObject> ParseQuery<T>.whereContainsText(key: String, query: String, caseSensitive: Boolean = false): ParseQuery<T> {
    return if (caseSensitive) {
        whereMatches(key, query)
    } else {
        whereMatches(key, query, "i")
    }
}

fun <T : Any> ParseObject.getJSONArrayValues(key: String): List<T> {
    val jsonArray = getJSONArray(key) ?: return listOf()
    val length = jsonArray.length().takeIf { it > 0 } ?: return listOf()
    val range = 0 until length
    return range.map { index -> jsonArray.get(index) as T }
}