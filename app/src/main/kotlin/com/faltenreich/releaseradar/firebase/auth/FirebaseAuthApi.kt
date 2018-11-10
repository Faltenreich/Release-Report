package com.faltenreich.releaseradar.firebase.auth

import com.google.firebase.auth.FirebaseUser

interface FirebaseAuthApi {

    val currentUser: FirebaseUser?

    val isSignedIn: Boolean

    fun login(onSuccess: ((FirebaseUser?) -> Unit)? = null, onError: ((Exception?) -> Unit)? = null)
}