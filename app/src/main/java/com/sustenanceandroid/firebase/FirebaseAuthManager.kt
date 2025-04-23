package com.sustenanceandroid.firebase

import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.auth.userProfileChangeRequest
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class FirebaseAuthManager {
    data class User(val id: String = "")

    private var auth: FirebaseAuth = Firebase.auth

    val currentUser: Flow<User?>
        get() = callbackFlow {
            val listener =
                FirebaseAuth.AuthStateListener { auth ->
                    this.trySend(auth.currentUser?.let { User(it.uid) })
                }
            auth.addAuthStateListener(listener)
            awaitClose { auth.removeAuthStateListener(listener) }
        }

    val isUserLoggedIn: Boolean
        get() = auth.currentUser != null

    val currentUserId: String
        get() = auth.currentUser?.uid.orEmpty()

    val currentUserDisplayName: String
        get() = auth.currentUser?.displayName ?: "user"

    suspend fun createAccount(email: String, password: String, callback: (Boolean) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                callback(task.isSuccessful)
            }.await()
    }

    suspend fun singIn(email: String, password: String, callback: (Boolean) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                callback(task.isSuccessful)
            }.await()
    }

    suspend fun updateProfile(name: String = "user") {
        if ((name == currentUserDisplayName || name == "user") && currentUserDisplayName.isNotEmpty()) return
        auth.currentUser?.updateProfile(
            userProfileChangeRequest {
                displayName = name
            }
        )?.await()
    }

    fun signOut() = auth.signOut()

    suspend fun deleteAccount() {
        auth.currentUser!!.delete().await()
    }
}
