package com.sustenanceandroid.firebase

import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class FirebaseManager {
    private var auth: FirebaseAuth = Firebase.auth

    private val currentUser = auth.currentUser

    fun isUserLoggedIn(): Boolean {
        return currentUser != null
    }

    fun createAccount(email: String, password: String): Boolean {
        var isCreateSuccessful = false
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    isCreateSuccessful = true
                }
            }
        return isCreateSuccessful
    }

    fun singIn(email: String, password: String): Boolean {
        var isSingSuccessful = false
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    isSingSuccessful = true
                }
            }
        return isSingSuccessful
    }

    fun getUserId(): String? {
        return currentUser?.uid
    }
}
