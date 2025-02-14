package com.sustenanceandroid.injection

import android.content.res.Resources
import com.sustenanceandroid.application.SustenanceApplication
import com.sustenanceandroid.firebase.FirebaseManager

object AppModule {

    lateinit var application: SustenanceApplication

    fun isInitialized() = ::application.isInitialized

    val resources: Resources by lazy { application.resources }

    val firebaseManager: FirebaseManager by lazy { FirebaseManager() }
}
