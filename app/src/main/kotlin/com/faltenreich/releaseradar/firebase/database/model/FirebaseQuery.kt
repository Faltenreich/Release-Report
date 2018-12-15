package com.faltenreich.releaseradar.firebase.database.model

// TODO: Find way to restrict data type
data class FirebaseQuery(
    val orderBy: String? = null,
    val equalTo: Any? = null,
    val startAt: Any? = null,
    val endAt: Any? = null
)