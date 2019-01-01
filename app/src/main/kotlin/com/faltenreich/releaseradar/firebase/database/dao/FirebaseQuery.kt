package com.faltenreich.releaseradar.firebase.database.dao

// TODO: Find way to restrict data type (e.g. via sealed class)
data class FirebaseQuery(
    val orderBy: String? = null,
    val equalTo: Any? = null,
    val limitToFirst: Int? = null,
    val limitToLast: Int? = null,
    val startAt: Pair<Any, String?>? = null,
    val endAt: Pair<Any, String?>? = null
)