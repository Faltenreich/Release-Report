package com.faltenreich.releaseradar.firebase.auth

import com.faltenreich.releaseradar.logging.LogLevel
import com.faltenreich.releaseradar.logging.log
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser

object FirebaseAuth {

    private val auth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }

    val currentUser: FirebaseUser?
        get() = auth.currentUser

    val isSignedIn: Boolean
        get() = currentUser != null

    fun login(onSuccess: ((FirebaseUser?) -> Unit)? = null, onError: ((Exception?) -> Unit)? = null) {
        when {
            !isSignedIn -> signInAnonymously(onSuccess, onError)
            else -> onSuccess?.invoke(currentUser)
        }
    }

    private fun signInAnonymously(onSuccess: ((FirebaseUser?) -> Unit)? = null, onError: ((Exception?) -> Unit)? = null) {
        auth.signInAnonymously().addOnCompleteListener { task: Task<AuthResult> ->
            when {
                task.isSuccessful -> {
                    log("Successfully signed in")
                    onSuccess?.invoke(currentUser)
                }
                else -> {
                    log("Failed to sign in: ${task.exception?.message}", LogLevel.ERROR)
                    onError?.invoke(task.exception)
                }
            }
        }
    }
}
