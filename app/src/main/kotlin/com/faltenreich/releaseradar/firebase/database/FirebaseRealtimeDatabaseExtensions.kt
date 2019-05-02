package com.faltenreich.releaseradar.firebase.database

/*
fun DatabaseReference.applyQuery(query: FirebaseQuery?): Query {
    query?.let { query ->
        val ordered = query.orderBy?.let { orderBy ->
            orderByChild(orderBy)
        } ?: this
        val filtered = query.equalTo?.let { equalTo ->
            when (equalTo) {
                is Boolean -> ordered.equalTo(equalTo)
                is Double -> ordered.equalTo(equalTo)
                is String -> ordered.equalTo(equalTo)
                else -> throw IllegalArgumentException("Unsupported data type for Query.equalTo(): $equalTo")
            }
        } ?: ordered
        val startedAt = query.startAt?.let { startAt ->
            when (startAt.first) {
                is Boolean -> filtered.startAt(startAt.first as Boolean, startAt.second)
                is Double -> filtered.startAt(startAt.first as Double, startAt.second)
                is String -> filtered.startAt(startAt.first as String, startAt.second)
                else -> throw IllegalArgumentException("Unsupported data type for Query.startAt(): $startAt")
            }
        } ?: filtered
        val endedAt = query.endAt?.let { endAt ->
            when (endAt.first) {
                is Boolean -> startedAt.endAt(endAt.first as Boolean, endAt.second)
                is Double -> startedAt.endAt(endAt.first as Double, endAt.second)
                is String -> startedAt.endAt(endAt.first as String, endAt.second)
                else -> throw IllegalArgumentException("Unsupported data type for Query.endAt(): $endAt")
            }
        } ?: startedAt
        val limitedToFirst = query.limitToFirst?.let { limit ->
            endedAt.limitToFirst(limit)
        } ?: endedAt
        val limitedToLast = query.limitToLast?.let { limit ->
            limitedToFirst.limitToLast(limit)
        } ?: limitedToFirst
        return limitedToLast
    } ?: return this
}
        */