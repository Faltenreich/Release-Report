package com.faltenreich.releaseradar.data.dao

import com.google.firebase.auth.FirebaseAuth

interface UserDependency {

    private val userId: String?
        get() = null // TODO: FirebaseAuth.currentUser?.uid

    val nodeRootPathForCurrentUser: String
        get() = "users/$userId"
}
