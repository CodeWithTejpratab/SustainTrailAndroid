package com.sustenanceandroid.module

import android.content.res.Resources
import com.sustenanceandroid.application.SustenanceApplication
import com.sustenanceandroid.firebase.FirebaseAuthManager
import com.sustenanceandroid.model.api.APIRepository

object AppModule {

    lateinit var application: SustenanceApplication

    val resources: Resources by lazy { application.resources }

    val firebaseAuthManager: FirebaseAuthManager by lazy { FirebaseAuthManager() }

    val apiRepository: APIRepository by lazy { APIRepository() }
}
