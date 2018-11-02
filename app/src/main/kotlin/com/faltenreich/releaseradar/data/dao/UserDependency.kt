package com.faltenreich.releaseradar.data.dao

import com.faltenreich.releaseradar.firebase.auth.FirebaseAuth

interface UserDependency {

    private val userId: String?
        get() = FirebaseAuth.currentUser?.uid

    val nodeRootPathForCurrentUser: String
        get() = "users/$userId"
}
