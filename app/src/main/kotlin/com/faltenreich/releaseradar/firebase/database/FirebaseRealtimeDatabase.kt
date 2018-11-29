package com.faltenreich.releaseradar.firebase.database

import com.google.firebase.database.*

internal object FirebaseRealtimeDatabase {

    private val database: FirebaseDatabase
        get() = FirebaseDatabase.getInstance()

    fun createReference(path: String): DatabaseReference = database.getReference(path)
}
