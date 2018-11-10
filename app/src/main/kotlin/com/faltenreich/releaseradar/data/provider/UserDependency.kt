package com.faltenreich.releaseradar.data.provider

import com.faltenreich.releaseradar.firebase.auth.FirebaseAuth

interface UserDependency {

    private val userId: String?
        get() = FirebaseAuth.currentUser?.uid

    val nodeRootPathForCurrentUser: String
        get() = "user/$userId"
}
