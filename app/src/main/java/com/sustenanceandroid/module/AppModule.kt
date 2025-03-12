package com.sustenanceandroid.module

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Resources
import com.sustenanceandroid.application.SustenanceApplication
import com.sustenanceandroid.event.EventHandler
import com.sustenanceandroid.firebase.FirebaseAuthManager

object AppModule {

    lateinit var application: SustenanceApplication

    fun isInitialized() = ::application.isInitialized

    private const val PREFS_FILE_NAME = "Config"

    val resources: Resources by lazy { application.resources }

    val eventHandler: EventHandler by lazy { EventHandler() }

    val firebaseAuthManager: FirebaseAuthManager by lazy { FirebaseAuthManager() }

    val context: Context by lazy { application.applicationContext }

    val sharedPreferences: SharedPreferences by lazy {
        application.getSharedPreferences(PREFS_FILE_NAME, Context.MODE_PRIVATE)
    }
}
