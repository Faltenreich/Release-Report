package com.faltenreich.releaseradar.firebase.database

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

internal object FirebaseRealtimeDatabase {

    private val database: FirebaseDatabase
        get() = FirebaseDatabase.getInstance().apply { setPersistenceEnabled(true) }

    fun createReference(path: String): DatabaseReference = database.getReference(path)
}
