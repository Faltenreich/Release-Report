package com.faltenreich.releaseradar.firebase.database.dao

// TODO: Find way to restrict data type (e.g. via sealed class)
data class FirebaseQuery(
    val orderBy: String? = null,
    val equalTo: Any? = null,
    val startAt: Any? = null,
    val endAt: Any? = null
)