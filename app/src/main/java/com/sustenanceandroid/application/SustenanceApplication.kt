package com.sustenanceandroid.application

import android.app.Application
import com.sustenanceandroid.module.AppModule

class SustenanceApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        AppModule.application = this
    }
}
