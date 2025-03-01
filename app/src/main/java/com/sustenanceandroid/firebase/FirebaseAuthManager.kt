package com.sustenanceandroid.firebase

import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
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
            Firebase.auth.addAuthStateListener(listener)
            awaitClose { Firebase.auth.removeAuthStateListener(listener) }
        }

    val isUserLoggedIn: Boolean
        get() = auth.currentUser != null

    val currentUserId: String
        get() = Firebase.auth.currentUser?.uid.orEmpty()

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

    fun signOut() = auth.signOut()

    suspend fun deleteAccount() {
        auth.currentUser!!.delete().await()
    }
}
