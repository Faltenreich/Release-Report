package com.faltenreich.releaseradar.firebase.database

import com.faltenreich.releaseradar.firebase.database.dao.FirebaseQuery
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.Query

fun DatabaseReference.query(query: FirebaseQuery): Query {
    val ordered = query.orderBy?.let { orderBy -> orderByChild(orderBy) } ?: this
    val filtered = query.equalTo?.let { equalTo ->
        when (equalTo) {
            is Boolean -> ordered.equalTo(equalTo)
            is Double -> ordered.equalTo(equalTo)
            is String -> ordered.equalTo(equalTo)
            else -> throw IllegalArgumentException("Unsupported data type for Query.equalTo(): $equalTo")
        }
    } ?: ordered
    val limitedFrom = query.startAt?.let { startAt ->
        when (startAt) {
            is Boolean -> filtered.startAt(startAt)
            is Double -> filtered.startAt(startAt)
            is String -> filtered.startAt(startAt)
            else -> throw IllegalArgumentException("Unsupported data type for Query.startAt(): $startAt")
        }
    } ?: filtered
    val limitedTo = query.endAt?.let { endAt ->
        when (endAt) {
            is Boolean -> limitedFrom.endAt(endAt)
            is Double -> limitedFrom.endAt(endAt)
            is String -> limitedFrom.endAt(endAt)
            else -> throw IllegalArgumentException("Unsupported data type for Query.endAt(): $endAt")
        }
    } ?: limitedFrom
    return limitedTo
}